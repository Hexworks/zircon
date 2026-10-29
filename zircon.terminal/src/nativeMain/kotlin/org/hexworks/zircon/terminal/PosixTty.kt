@file:OptIn(ExperimentalForeignApi::class)

package org.hexworks.zircon.terminal

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.usePinned
import org.hexworks.zircon.api.data.Size
import platform.posix.EINTR
import platform.posix.POLLIN
import platform.posix.SIGHUP
import platform.posix.SIGTERM
import platform.posix.SIGWINCH
import platform.posix.SIG_DFL
import platform.posix.STDIN_FILENO
import platform.posix.STDOUT_FILENO
import platform.posix.TCSAFLUSH
import platform.posix.TIOCGWINSZ
import platform.posix.cfmakeraw
import platform.posix.errno
import platform.posix.ioctl
import platform.posix.isatty
import platform.posix.poll
import platform.posix.pollfd
import platform.posix.read
import platform.posix.signal
import platform.posix.tcgetattr
import platform.posix.tcsetattr
import platform.posix.termios
import platform.posix.winsize
import platform.posix.write
import kotlin.concurrent.Volatile

@Volatile
private var resizePending = false

@Volatile
private var terminatePending = false

public actual fun systemTty(): Tty = PosixTty()

/** The POSIX terminal: termios for raw mode, `ioctl` for the size, `poll` + `read` for input. */
internal class PosixTty : Tty {

    private val saved = nativeHeap.alloc<termios>()
    private var raw = false

    override fun open() {
        check(isatty(STDIN_FILENO) != 0) { "stdin is not a terminal" }
        check(tcgetattr(STDIN_FILENO, saved.ptr) == 0) { "tcgetattr failed (errno $errno)" }
        memScoped {
            val mode = alloc<termios>()
            tcgetattr(STDIN_FILENO, mode.ptr)
            cfmakeraw(mode.ptr)
            check(tcsetattr(STDIN_FILENO, TCSAFLUSH, mode.ptr) == 0) { "tcsetattr failed (errno $errno)" }
        }
        raw = true
        signal(SIGWINCH, staticCFunction { _: Int -> resizePending = true })
        signal(SIGTERM, staticCFunction { _: Int -> terminatePending = true })
        signal(SIGHUP, staticCFunction { _: Int -> terminatePending = true })
    }

    override fun close() {
        if (!raw) return
        tcsetattr(STDIN_FILENO, TCSAFLUSH, saved.ptr)
        signal(SIGWINCH, SIG_DFL)
        signal(SIGTERM, SIG_DFL)
        signal(SIGHUP, SIG_DFL)
        raw = false
    }

    override fun size(): Size = memScoped {
        val ws = alloc<winsize>()
        if (ioctl(STDOUT_FILENO, TIOCGWINSZ.toULong(), ws.ptr) == 0 && ws.ws_col > 0u) {
            Size(ws.ws_col.toInt(), ws.ws_row.toInt())
        } else {
            Size(80, 24)
        }
    }

    override fun read(buffer: ByteArray, timeoutMillis: Int): Int = memScoped {
        val fds = alloc<pollfd>()
        fds.fd = STDIN_FILENO
        fds.events = POLLIN.toShort()
        val ready = poll(fds.ptr, 1.convert(), timeoutMillis)
        if (ready == 0) return 0
        if (ready < 0) return if (errno == EINTR) 0 else -1
        val n = buffer.usePinned { read(STDIN_FILENO, it.addressOf(0), buffer.size.convert()) }
        if (n <= 0) -1 else n.toInt()
    }

    override fun write(bytes: ByteArray, length: Int) {
        var offset = 0
        bytes.usePinned { pinned ->
            while (offset < length) {
                val n = write(STDOUT_FILENO, pinned.addressOf(offset), (length - offset).convert())
                if (n < 0) {
                    if (errno == EINTR) continue
                    error("write to the terminal failed (errno $errno)")
                }
                offset += n.toInt()
            }
        }
    }

    override fun takeResize(): Boolean {
        val pending = resizePending
        resizePending = false
        return pending
    }

    override val terminateRequested: Boolean get() = terminatePending
}
