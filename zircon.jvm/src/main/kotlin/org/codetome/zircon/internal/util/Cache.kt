package org.codetome.zircon.internal.util

import org.codetome.zircon.internal.behavior.Cacheable
import org.codetome.zircon.polyfills.Option

/**
 * Simple cache interface for storing [Cacheable]s.
 */
actual interface Cache<R : Cacheable> {

    /**
     * Retrieves a font region by a [TextCharacter] if present.
     */
    actual fun retrieveIfPresent(key: String): Option<R>

    /**
     * Caches the given [Cacheable] and then returns it.
     */
    actual fun store(cacheable: R): R

    actual companion object {
        actual fun <R : Cacheable> create(): Cache<R> {
            return DefaultCache()
        }
    }

}
