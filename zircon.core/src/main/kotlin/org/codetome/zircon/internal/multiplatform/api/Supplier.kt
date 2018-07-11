package org.codetome.zircon.internal.multiplatform.api

interface Supplier<out T> {

    fun get(): T
}
