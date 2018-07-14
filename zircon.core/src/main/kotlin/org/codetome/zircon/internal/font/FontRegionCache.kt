package org.codetome.zircon.internal.font

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.util.Maybe

/**
 * This cache is responsible for caching font regions. A font region is basically
 * an image representing a character graphically.
 */
interface FontRegionCache<R> {

    /**
     * Retrieves a font region by a [TextCharacter] if present.
     */
    fun retrieveIfPresent(textCharacter: TextCharacter): Maybe<R>

    /**
     * Caches the given region for a [TextCharacter].
     */
    fun store(textCharacter: TextCharacter, region: R)
}
