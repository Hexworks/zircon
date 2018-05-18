package org.codetome.zircon.util

interface Consumer<in T> {

    fun accept(t: T)
}
