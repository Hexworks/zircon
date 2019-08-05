package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultDrawSurfaceSnapshot

/**
 * Represents the contents of a [DrawSurface] at a given moment in time.
 */
interface DrawSurfaceSnapshot {

    val tiles: Map<Position, Tile>
    val tileset: TilesetResource
    val size: Size

    operator fun component1() = tiles

    operator fun component2() = tileset

    operator fun component3() = size

    companion object {

        /**
         * Creates a new [DrawSurfaceSnapshot].
         */
        fun create(tiles: Map<Position, Tile>, tileset: TilesetResource, size: Size): DrawSurfaceSnapshot {
            return DefaultDrawSurfaceSnapshot(tiles, tileset, size)
        }
    }
}
