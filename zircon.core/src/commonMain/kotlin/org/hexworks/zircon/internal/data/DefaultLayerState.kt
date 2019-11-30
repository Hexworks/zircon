package org.hexworks.zircon.internal.data

import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultLayerState(
        override val tiles: Map<Position, Tile>,
        override val tileset: TilesetResource,
        override val size: Size,
        override val id: Identifier,
        override val position: Position,
        override val isHidden: Boolean) : LayerState {

    override fun toString(): String {
        return DrawSurfaces.tileGraphicsBuilder()
                .withSize(size)
                .withTiles(tiles)
                .build()
                .toString()
    }
}
