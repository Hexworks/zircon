package org.codetome.zircon.internal.font

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.util.Maybe

/**
 * This cache is responsible for caching font regions. A font region is basically
 * an image representing a character graphically.
 */
interface FontRegionCache<R> {

    /**
     * Retrieves a font region by a [Tile] if present.
     */
    fun retrieveIfPresent(tile: Tile): Maybe<R>

    /**
     * Caches the given region for a [Tile].
     */
    fun store(tile: Tile, region: R)
}
