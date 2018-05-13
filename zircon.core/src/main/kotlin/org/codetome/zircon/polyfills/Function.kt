package org.codetome.zircon.polyfills

interface Function<T, R> {

    fun apply(t: T): R

    fun <V> compose(before: Function<in V, out T>): Function<V, R> {
        val outer = this
        return object : Function<V, R> {
            override fun apply(v: V): R {
                return outer.apply(before.apply(v))
            }
        }
    }

    fun <V> andThen(after: Function<in R, out V>): Function<T, V> {
        val outer = this
        return object : Function<T, V> {
            override fun apply(t: T): V {
                return after.apply(outer.apply(t))
            }
        }
    }

    companion object {

        fun <T> identity(): Function<T, T> {
            return object : Function<T, T> {
                override fun apply(t: T): T {
                    return t
                }
            }
        }
    }
}
