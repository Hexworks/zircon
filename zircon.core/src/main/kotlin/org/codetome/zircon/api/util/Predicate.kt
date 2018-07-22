package org.codetome.zircon.api.util

interface Predicate<T> {

    fun test(t: T): Boolean

    fun and(other: Predicate<in T>): Predicate<T> {
        val outer = this
        return object : Predicate<T> {
            override fun test(t: T): Boolean {
                return outer.test(t) && other.test(t)
            }
        }
    }

    fun negate(): Predicate<T> {
        val outer = this
        return object : Predicate<T> {
            override fun test(t: T): Boolean {
                return !outer.test(t)
            }
        }
    }

    fun or(other: Predicate<in T>): Predicate<T> {
        val outer = this
        return object : Predicate<T> {
            override fun test(t: T): Boolean {
                return outer.test(t) || other.test(t)
            }
        }
    }

    companion object {

        fun <T> isEqual(targetRef: Any): Predicate<T?> {
            return object : Predicate<T?> {
                override fun test(t: T?): Boolean {
                    return targetRef == t
                }
            }
        }
    }
}
