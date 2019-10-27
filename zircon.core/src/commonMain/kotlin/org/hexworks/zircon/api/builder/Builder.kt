package org.hexworks.zircon.api.builder

import org.hexworks.zircon.api.behavior.Copiable

interface Builder<out T> : Copiable<Builder<T>> {

    fun build(): T
}
