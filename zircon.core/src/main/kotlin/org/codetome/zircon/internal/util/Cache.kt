package org.codetome.zircon.internal.util

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.behavior.Cacheable
import java.util.*

/**
 * Simple cache interface for storing [Cacheable]s.
 */
interface Cache<R: Cacheable> {

    /**
     * Retrieves a font region by a [TextCharacter] if present.
     */
    fun retrieveIfPresent(key: String): Optional<R>

    /**
     * Caches the given [Cacheable] and then returns it.
     */
    fun store(cacheable: R) : R
}
