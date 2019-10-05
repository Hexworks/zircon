package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultTileGraphicsState

/**
 * Represents the contents of a [TileGraphics] at a given moment in time.
 */
interface TileGraphicsState {

    val tiles: Map<Position, Tile>
    val tileset: TilesetResource
    val size: Size

    operator fun component1() = tiles

    operator fun component2() = tileset

    operator fun component3() = size

    companion object {

        /**
         * Creates a new [TileGraphicsState].
         */
        fun create(tiles: Map<Position, Tile>, tileset: TilesetResource, size: Size): TileGraphicsState {
            return DefaultTileGraphicsState(tiles, tileset, size)
        }
    }
}
