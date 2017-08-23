package org.codetome.zircon.api.builder

interface Builder<out T> {

    fun build(): T
}