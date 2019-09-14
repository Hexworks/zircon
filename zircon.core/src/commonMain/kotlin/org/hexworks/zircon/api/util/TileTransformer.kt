package org.hexworks.zircon.api.util

import org.hexworks.zircon.api.data.Tile

/**
 * SAM interface for transforming [Tile]s.
 */
interface TileTransformer {

    /**
     * Transforms the given [tile] to a new one.
     */
    operator fun invoke(tile: Tile): Tile
}
