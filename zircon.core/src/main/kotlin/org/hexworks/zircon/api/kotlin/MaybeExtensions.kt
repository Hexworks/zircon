package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Function
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.Supplier

/**
 * Extension function which adapts [Maybe.ifPresent] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T> Maybe<T>.ifPresent(fn: (T) -> Unit) {
    return ifPresent(object : Consumer<T> {
        override fun accept(value: T) {
            fn.invoke(value)
        }
    })
}

/**
 * Extension function which adapts [Maybe.map] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T, U> Maybe<T>.map(fn: (T) -> U): Maybe<U> {
    return map(object : Function<T, U> {
        override fun apply(param: T): U {
            return fn.invoke(param)
        }
    })
}

/**
 * Extension function which adapts [Maybe.flatMap] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T, U> Maybe<T>.flatMap(fn: (T) -> Maybe<U>): Maybe<U> {
    return flatMap(object : Function<T, Maybe<U>> {
        override fun apply(param: T): Maybe<U> {
            return fn.invoke(param)
        }
    })
}

/**
 * Extension function which adapts [Maybe.fold] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T, U> Maybe<T>.fold(whenEmpty: () -> U, whenPresent: (T) -> U): U {
    return fold(object : Supplier<U> {
        override fun get(): U {
            return whenEmpty.invoke()
        }
    }, object : Function<T, U> {
        override fun apply(param: T): U {
            return whenPresent.invoke(param)
        }
    })
}

/**
 * Extension function which adapts [Maybe.orElseGet] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T> Maybe<T>.orElseGet(fn: () -> T): T {
    return orElseGet(object : Supplier<T> {
        override fun get(): T {
            return fn.invoke()
        }
    })
}

/**
 * Extension function which adapts [Maybe.orElseThrow] to
 * Kotlin idioms (eg: lambdas).
 */
fun <T, X : Exception> Maybe<T>.orElseThrow(fn: () -> X): T {
    return orElseThrow(object : Supplier<X> {
        override fun get(): X {
            throw fn.invoke()
        }
    })
}












