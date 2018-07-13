package org.codetome.zircon.internal.multiplatform.util

expect object SystemUtils {

    fun getCurrentTimeMs(): Long

    fun getLineSeparator(): String
}
