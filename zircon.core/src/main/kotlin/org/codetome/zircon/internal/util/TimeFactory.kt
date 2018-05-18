package org.codetome.zircon.internal.util

expect object TimeFactory {

    fun getCurrentTimeMs(): Long
}
