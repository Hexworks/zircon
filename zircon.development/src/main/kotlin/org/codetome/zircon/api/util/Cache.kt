package org.codetome.zircon.api.util

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.platform.factory.CacheFactory

/**
 * Simple cache interface for storing objects.
 */
interface Cache<T> {

    /**
     * Retrieves a tileset region by a `key` if present.
     */
    fun retrieveIfPresent(key: String): Maybe<T>

    /**
     * Caches the given object and then returns it.
     */
    fun store(key: String, obj: T): T

    companion object {

        /**
         * Creates a new [Cache] for the given type.
         */
        fun <T : Cacheable> create(): Cache<T> = CacheFactory.create()
    }
}
