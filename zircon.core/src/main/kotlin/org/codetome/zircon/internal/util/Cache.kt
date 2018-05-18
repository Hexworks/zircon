package org.codetome.zircon.internal.util

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.util.Maybe

/**
 * Simple cache interface for storing [Cacheable]s.
 */
interface Cache<R : Cacheable> {

    /**
     * Retrieves a font region by a [TextCharacter] if present.
     */
    fun retrieveIfPresent(key: String): Maybe<R>

    /**
     * Caches the given [Cacheable] and then returns it.
     */
    fun store(cacheable: R): R

    companion object : CacheCompanion
}
