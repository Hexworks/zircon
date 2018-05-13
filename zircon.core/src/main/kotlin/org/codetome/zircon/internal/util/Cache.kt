package org.codetome.zircon.internal.util

import org.codetome.zircon.internal.behavior.Cacheable
import org.codetome.zircon.polyfills.Option

/**
 * Simple cache interface for storing [Cacheable]s.
 */
expect interface Cache<R : Cacheable> {

    /**
     * Retrieves a font region by a [TextCharacter] if present.
     */
    fun retrieveIfPresent(key: String): Option<R>

    /**
     * Caches the given [Cacheable] and then returns it.
     */
    fun store(cacheable: R): R

    companion object {

        fun <R : Cacheable> create(): Cache<R>
    }
}
