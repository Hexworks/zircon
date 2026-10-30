package org.hexworks.zircon.compose.tileset

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.compose.FrameData

/**
 * Stands in for a tileset the Compose renderer cannot create (no factory for the resource's
 * tile and tileset type): every tile drawn with it becomes a cell of [MISSING_TILE_COLOR],
 * so the mistake is visible on screen and the frame still renders.
 */
public class PlaceholderTileset(resource: TilesetResource) : ComposeTileset(resource) {

    override suspend fun load() {
        loaded = true
    }

    override fun drawTile(tile: Tile, context: FrameData, position: Position) {
        context.writeBackground(position, MISSING_TILE_COLOR)
    }

    public companion object {
        /** The magenta of a missing texture. */
        public val MISSING_TILE_COLOR: Color = Color.create(255, 0, 255)
    }
}
