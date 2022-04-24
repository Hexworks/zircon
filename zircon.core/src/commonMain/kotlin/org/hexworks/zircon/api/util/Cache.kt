package org.hexworks.zircon.api.util

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.internal.util.DefaultCache
import kotlin.time.ExperimentalTime

/**
 * Simple cache interface for storing objects.
 */
interface Cache<T> {

    /**
     * Retrieves a stored object by a [key] if present or returns `null` if not.
     */
    fun retrieveIfPresentOrNull(key: String): T?

    /**
     * Retrieves a stored object by a [key] if present or returns
     * the result of calling [orElse] with the [key] if not.
     */
    fun retrieveIfPresentOrElse(key: String, orElse: (key: String) -> T): T {
        return retrieveIfPresentOrNull(key) ?: orElse(key)
    }

    /**
     * Caches the given object and then returns it.
     */
    fun store(obj: T): T

    companion object {

        /**
         * Creates a new [Cache] for the given type.
         */
        fun <T : Cacheable> create(): Cache<T> = DefaultCache()
    }
}
