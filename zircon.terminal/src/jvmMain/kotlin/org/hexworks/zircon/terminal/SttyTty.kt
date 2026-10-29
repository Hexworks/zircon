package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.data.Size
import sun.misc.Signal
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream

public actual fun systemTty(): Tty = SttyTty()

/**
 * The JVM terminal, without native code: `stty` on `/dev/tty` for raw mode and the size,
 * `available()` polling for timed reads, `sun.misc.Signal` for resize and termination.
 */
internal class SttyTty : Tty {

    private val input = FileInputStream(FileDescriptor.`in`)
    private val output = FileOutputStream(FileDescriptor.out)
    private var savedMode: String? = null

    @Volatile
    private var resizePending = false

    @Volatile
    private var terminatePending = false

    override fun open() {
        check(File("/dev/tty").exists()) { "no controlling terminal (/dev/tty)" }
        savedMode = stty("-g").trim()
        stty("raw", "-echo")
        Signal.handle(Signal("WINCH")) { resizePending = true }
        Signal.handle(Signal("TERM")) { terminatePending = true }
        Signal.handle(Signal("HUP")) { terminatePending = true }
    }

    override fun close() {
        val mode = savedMode ?: return
        stty(mode)
        savedMode = null
    }

    override fun size(): Size {
        val parts = stty("size").trim().split(' ')
        val rows = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val cols = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return if (cols > 0 && rows > 0) Size(cols, rows) else Size(80, 24)
    }

    override fun read(buffer: ByteArray, timeoutMillis: Int): Int {
        val deadline = System.nanoTime() + timeoutMillis * 1_000_000L
        while (input.available() == 0) {
            if (System.nanoTime() >= deadline) return 0
            Thread.sleep(1)
        }
        val n = input.read(buffer, 0, minOf(buffer.size, input.available()))
        return if (n <= 0) -1 else n
    }

    override fun write(bytes: ByteArray, length: Int) {
        output.write(bytes, 0, length)
        output.flush()
    }

    override fun takeResize(): Boolean {
        val pending = resizePending
        resizePending = false
        return pending
    }

    override val terminateRequested: Boolean get() = terminatePending

    private fun stty(vararg args: String): String {
        val process = ProcessBuilder("stty", *args)
            .redirectInput(ProcessBuilder.Redirect.from(File("/dev/tty")))
            .redirectErrorStream(true)
            .start()
        val text = process.inputStream.readBytes().decodeToString()
        check(process.waitFor() == 0) { "stty ${args.joinToString(" ")} failed: $text" }
        return text
    }
}
