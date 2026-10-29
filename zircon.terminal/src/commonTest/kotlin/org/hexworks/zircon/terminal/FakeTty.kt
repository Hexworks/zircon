package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.data.Size

/**
 * A scripted terminal: each [read] hands out the next chunk of [reads]; once they run out
 * the next [idleReads] calls time out and the one after reports end of input. Everything
 * written is kept in [output].
 */
internal class FakeTty(
    private var size: Size = Size(80, 24),
    reads: List<ByteArray> = emptyList(),
    private val idleReads: Int = 3,
) : Tty {

    private val reads = ArrayDeque(reads)
    private var idle = 0
    private val written = ByteSink(1024)
    var opened = false
    var closed = false
    var resize = false
    override var terminateRequested: Boolean = false

    val output: ByteArray get() = written.toByteArray()
    val outputText: String get() = output.decodeToString().readable()

    override fun open() {
        opened = true
    }

    override fun close() {
        closed = true
    }

    override fun size(): Size = size

    fun resizeTo(newSize: Size) {
        size = newSize
        resize = true
    }

    override fun read(buffer: ByteArray, timeoutMillis: Int): Int {
        val next = reads.removeFirstOrNull()
        if (next != null) {
            next.copyInto(buffer)
            return next.size
        }
        return if (idle++ < idleReads) 0 else -1
    }

    override fun write(bytes: ByteArray, length: Int) {
        for (i in 0 until length) written.byte(bytes[i].toInt())
    }

    override fun takeResize(): Boolean = resize.also { resize = false }
}

/** ESC shown as `␛` so sequences can be read and compared in failure messages. */
internal fun String.readable(): String = replace("\u001b", "␛")

internal fun bytes(text: String): ByteArray = text.encodeToByteArray()
