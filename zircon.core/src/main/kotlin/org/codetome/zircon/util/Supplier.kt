package org.codetome.zircon.util

interface Supplier<out T> {

    fun get(): T
}
