package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.VirtualTileset
import org.hexworks.zircon.platform.util.SystemUtils

@Suppress("UNCHECKED_CAST", "UNUSED_VARIABLE", "unused")
class VirtualRenderer(
        private val tileGrid: InternalTileGrid
) : Renderer, Closeable by tileGrid {

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
        tileGrid.renderables.forEach {
            val tg = FastTileGraphics(
                    initialSize = it.size,
                    initialTileset = it.tileset,
                    initialTiles = mapOf()
            )
            it.render(tg)
            renderTiles(tg)
        }
        tileGrid.updateAnimations(now, tileGrid)
        lastRender = now
    }

    private fun renderTiles(graphics: FastTileGraphics) {
        graphics.contents().forEach { (pos, tile) ->
            tileset.drawTile(tile, 'x', pos)
        }
    }

}
