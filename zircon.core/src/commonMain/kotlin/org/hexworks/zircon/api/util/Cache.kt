package org.hexworks.zircon.api.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.platform.factory.CacheFactory

/**
 * Simple cache interface for storing objects.
 */
interface Cache<T> {

    /**
     * Retrieves a tileset region by a `key` if present.
     */
    @Deprecated("Use the orNull construct instead", ReplaceWith("retrieveIfPresentOrNull(key)"))
    fun retrieveIfPresent(key: String): Maybe<T>

    /**
     * Retrieves a tileset region by a `key` if present.
     */
    fun retrieveIfPresentOrNull(key: String): T?

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
