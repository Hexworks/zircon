package org.codetome.zircon.api.util

interface Supplier<out T> {

    fun get(): T
}
