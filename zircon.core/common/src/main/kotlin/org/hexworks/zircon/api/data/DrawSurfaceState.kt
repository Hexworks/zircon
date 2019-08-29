package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultDrawSurfaceState

/**
 * Represents the contents of a [DrawSurface] at a given moment in time.
 */
interface DrawSurfaceState {

    val tiles: Map<Position, Tile>
    val tileset: TilesetResource
    val size: Size

    operator fun component1() = tiles

    operator fun component2() = tileset

    operator fun component3() = size

    companion object {

        /**
         * Creates a new [DrawSurfaceState].
         */
        fun create(tiles: Map<Position, Tile>, tileset: TilesetResource, size: Size): DrawSurfaceState {
            return DefaultDrawSurfaceState(tiles, tileset, size)
        }
    }
}
