package org.hexworks.zircon.api

import org.hexworks.zircon.api.util.Maybe

object Maybes {

    @JvmStatic
    fun <T> empty(): Maybe<T> {
        return Maybe.empty()
    }

    @JvmStatic
    fun <T> of(value: T): Maybe<T> {
        return Maybe.of(value)
    }

    @JvmStatic
    fun <T> ofNullable(value: T?): Maybe<T> {
        return Maybe.ofNullable(value)
    }
}
