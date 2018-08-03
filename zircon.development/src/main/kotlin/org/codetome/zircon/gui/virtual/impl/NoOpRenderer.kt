package org.codetome.zircon.gui.virtual.impl

import org.codetome.zircon.api.application.Renderer
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.tileset.impl.VirtualTileset
import java.awt.image.BufferedImage

@Suppress("UNCHECKED_CAST")
class NoOpRenderer(private val grid: TileGrid) : Renderer {

    private val tileset = VirtualTileset()

    override fun create() {

    }

    override fun dispose() {
        grid.close()
    }

    override fun render() {
        renderTiles(grid.createSnapshot(), tileset)
        grid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.createSnapshot(),
                    tileset = tileset)
        }
    }

    private fun renderTiles(tiles: Map<Position, Tile>,
                            tileset: Tileset<out Tile, out Any>) {
        tiles.forEach { (pos, tile) ->
            val (x, y) = pos.toAbsolutePosition(tileset)

            val texture = this.tileset.fetchTextureForTile(tile)
            // we do nothing with it
        }
    }
}
