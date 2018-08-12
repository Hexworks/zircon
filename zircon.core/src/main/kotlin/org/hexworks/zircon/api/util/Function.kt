package org.hexworks.zircon.api.util

interface Function<in T, out R> {

    fun apply(param: T): R
}
