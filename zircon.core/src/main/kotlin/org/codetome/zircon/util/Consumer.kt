package org.codetome.zircon.util

interface Consumer<T> {

    fun accept(t: T)

    fun andThen(after: Consumer<in T>): Consumer<T> {
        val outer = this
        return object : Consumer<T> {
            override fun accept(t: T) {
                outer.accept(t)
                after.accept(t)
            }
        }
    }
}
