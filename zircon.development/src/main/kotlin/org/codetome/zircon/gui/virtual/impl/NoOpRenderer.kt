package org.codetome.zircon.gui.virtual.impl

import org.codetome.zircon.api.application.Renderer
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import java.awt.image.BufferedImage

@Suppress("UNCHECKED_CAST")
class NoOpRenderer(private val grid: TileGrid<out Any, out Any>) : Renderer {

    private val tileset = grid.tileset() as Tileset<Any, Any>

    override fun create() {

    }

    override fun dispose() {
        grid.close()
    }

    override fun render() {
        renderTiles(grid.createSnapshot(), tileset)
        grid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.createSnapshot(), // TODO: fix cat
                    tileset = layer.tileset() as Tileset<Any, Any>)
        }
    }

    private fun renderTiles(tiles: Map<Position, Tile<out Any>>,
                            tileset: Tileset<Any, Any>) {
        tiles.forEach { (pos, tile) ->
            val (x, y) = pos.toAbsolutePosition(tileset)

            val texture = tileset.fetchTextureForTile(tile as Tile<Any>)
            // we do nothing with it
        }
    }
}
