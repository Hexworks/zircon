package org.codetome.zircon.internal.multiplatform.api

interface Consumer<in T> {

    fun accept(t: T)
}
