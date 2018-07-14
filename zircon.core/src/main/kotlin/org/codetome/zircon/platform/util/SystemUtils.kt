package org.codetome.zircon.platform.util

expect object SystemUtils {

    fun getCurrentTimeMs(): Long

    fun getLineSeparator(): String
}
