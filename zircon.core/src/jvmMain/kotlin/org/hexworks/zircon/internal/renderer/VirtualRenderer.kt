package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.VirtualTileset
import org.hexworks.zircon.platform.util.SystemUtils

@Suppress("UNCHECKED_CAST", "UNUSED_VARIABLE", "unused")
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
        val snapshot = tileGrid.createSnapshot()
        tileGrid.updateAnimations(now, tileGrid)
        renderTiles(snapshot)
        tileGrid.layers.forEach { layer ->
            renderTiles(layer.createSnapshot())
        }
        lastRender = now
    }

    private fun renderTiles(snapshot: Snapshot) {
        snapshot.cells.forEach { (pos, tile) ->
            if (tile !== Tile.empty()) {
                tileset.drawTile(tile, 'x', pos)
            }
        }
    }

}
