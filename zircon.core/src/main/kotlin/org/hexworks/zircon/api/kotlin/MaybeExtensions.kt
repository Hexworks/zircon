package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Function
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.Supplier

/**
 * Extension function which adapts [Maybe.ifPresent] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun <T> Maybe<T>.ifPresent(crossinline fn: (T) -> Unit) {
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
inline fun <T, U> Maybe<T>.map(crossinline fn: (T) -> U): Maybe<U> {
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
inline fun <T, U> Maybe<T>.flatMap(crossinline fn: (T) -> Maybe<U>): Maybe<U> {
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
inline fun <T, U> Maybe<T>.fold(crossinline whenEmpty: () -> U, crossinline whenPresent: (T) -> U): U {
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
inline fun <T> Maybe<T>.orElseGet(crossinline fn: () -> T): T {
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
inline fun <T, X : Exception> Maybe<T>.orElseThrow(crossinline fn: () -> X): T {
    return orElseThrow(object : Supplier<X> {
        override fun get(): X {
            throw fn.invoke()
        }
    })
}












