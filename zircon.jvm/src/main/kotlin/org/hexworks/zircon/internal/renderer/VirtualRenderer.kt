package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.VirtualTileset
import org.hexworks.zircon.platform.util.SystemUtils

@Suppress("UNCHECKED_CAST")
class VirtualRenderer(private val tileGrid: InternalTileGrid) : Renderer {

    val config = RuntimeConfig.config
    private var firstDraw = true
    private val tileset = VirtualTileset()
    private var blinkOn = true
    private var lastRender: Long = SystemUtils.getCurrentTimeMs()
    private var lastBlink: Long = lastRender

    override fun create() {

    }

    override fun close() {
        tileGrid.close()
    }

    override fun render() {
        val now = SystemUtils.getCurrentTimeMs()
        val snapshot = tileGrid.snapshot()
        tileGrid.updateAnimations(now, tileGrid)
        renderTiles(
                tiles = snapshot)
        tileGrid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.snapshot())
        }
        lastRender = now
    }

    private fun renderTiles(tiles: Map<Position, Tile>) {
        tiles.forEach { (pos, tile) ->
            if (tile !== Tile.empty()) {
                val (x, y) = pos.toAbsolutePosition(tileset)
                val texture = tileset.fetchTextureForTile(tile)
            }
        }
    }

    private fun getWidth() = tileGrid.widthInPixels()

    private fun getHeight() = tileGrid.heightInPixels()

}
