package org.hexworks.zircon.api.util

interface Consumer<in T> {

    fun accept(t: T)
}
