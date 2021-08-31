package org.hexworks.zircon.api.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.platform.factory.CacheFactory

/**
 * Simple cache interface for storing objects.
 */
interface Cache<T> {

    @Deprecated("Use the orNull construct instead", ReplaceWith("retrieveIfPresentOrNull(key)"))
    fun retrieveIfPresent(key: String): Maybe<T>

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
    @Deprecated("T is already cacheable and has a key", replaceWith = ReplaceWith("store(obj)"))
    fun store(key: String, obj: T): T

    /**
     * Caches the given object and then returns it.
     */
    fun store(obj: T): T

    companion object {

        /**
         * Creates a new [Cache] for the given type.
         */
        fun <T : Cacheable> create(): Cache<T> = CacheFactory.create()
    }
}
