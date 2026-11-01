package org.hexworks.zircon.terminal

import org.hexworks.cobalt.logging.api.LogLevel
import org.hexworks.cobalt.logging.api.LogSink

/**
 * Holds log statements while the alternate screen is active, since printing them there would
 * scribble over the frame. Keeps the last [capacity] entries; [drain] hands them out in the
 * console sink's format, headed by a count of the ones that were dropped.
 */
internal class BufferedLogSink(private val capacity: Int = 1000) : LogSink {

    private val entries = ArrayDeque<String>()
    private var dropped = 0

    override fun log(level: LogLevel, loggerName: String, message: String, throwable: Throwable?) {
        val text = StringBuilder("[$level] $loggerName: $message\n")
        throwable?.let { text.append(it.stackTraceToString()) }
        if (entries.size == capacity) {
            entries.removeFirst()
            dropped++
        }
        entries.addLast(text.toString())
    }

    /** Everything logged so far as one block of text, or `null` when nothing was. Empties the buffer. */
    fun drain(): String? {
        if (entries.isEmpty()) return null
        val text = StringBuilder()
        if (dropped > 0) text.append("... $dropped earlier log lines dropped\n")
        entries.forEach { text.append(it) }
        entries.clear()
        dropped = 0
        return text.toString()
    }
}
