package org.hexworks.zircon.api.util

interface Supplier<out T> {

    fun get(): T
}
