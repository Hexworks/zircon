package org.hexworks.zircon.platform.util

expect object SystemUtils {

    fun getCurrentTimeMs(): Long

    fun getLineSeparator(): String

    fun measureNanoTime(block: () -> Unit): Long
}
