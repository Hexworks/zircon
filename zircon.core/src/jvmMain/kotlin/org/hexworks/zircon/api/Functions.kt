package org.hexworks.zircon.api

import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 * Utility functions that can be used to create functions from **Java** functional classes.
 */
object Functions {

    /**
     * Creates a Kotlin [Function1] from the given [Consumer].
     */
    @JvmStatic
    fun <T> fromConsumer(callable: Consumer<T>): Function1<T, Unit> {
        return { t ->
            callable.accept(t)
        }
    }

    /**
     * Creates a Kotlin [Function2] from the given [BiConsumer].
     */
    @JvmStatic
    fun <T, U> fromBiConsumer(callable: BiConsumer<T, U>): Function2<T, U, Unit> {
        return { t, u ->
            callable.accept(t, u)
        }
    }
}
