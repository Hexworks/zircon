package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultLayerSnapshot

/**
 * Represents the contents of a [Layer] at a given moment in time.
 */
interface LayerSnapshot : DrawSurfaceSnapshot {

    val position: Position

    operator fun component4() = position

    companion object {

        /**
         * Creates a new [LayerSnapshot].
         */
        fun create(tiles: Map<Position, Tile>,
                   tileset: TilesetResource,
                   size: Size,
                   position: Position): LayerSnapshot {
            return DefaultLayerSnapshot(tiles, tileset, size, position)
        }
    }
}
