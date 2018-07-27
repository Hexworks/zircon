package org.codetome.zircon.internal.tileset

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.util.Maybe

/**
 * This cache is responsible for caching tileset regions. A tileset region is basically
 * an image representing a character graphically.
 */
interface FontRegionCache<R> {

    /**
     * Retrieves a tileset region by a [Tile] if present.
     */
    fun retrieveIfPresent(tile: Tile): Maybe<R>

    /**
     * Caches the given region for a [Tile].
     */
    fun store(tile: Tile, region: R)
}
