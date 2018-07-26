package org.codetome.zircon.api.util

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.platform.factory.CacheFactory

/**
 * Simple cache interface for storing objects.
 */
interface Cache<R> {

    /**
     * Retrieves a font region by a `key` if present.
     */
    fun retrieveIfPresent(key: String): Maybe<R>

    /**
     * Caches the given object and then returns it.
     */
    fun store(key: String, obj: R): R

    companion object {

        /**
         * Creates a new [Cache] for the given type.
         */
        fun <R : Cacheable> create(): Cache<R> = CacheFactory.create()
    }
}
