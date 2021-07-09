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

    private val tileset = VirtualTileset()
    private var lastRender: Long = SystemUtils.getCurrentTimeMs()

    override fun create() {

    }

    override fun close() {
        tileGrid.close()
    }

    override fun render() {
        val now = SystemUtils.getCurrentTimeMs()
        tileGrid.updateAnimations(now, tileGrid)
        tileGrid.renderables.forEach { renderable ->
            if (!renderable.isHidden) {
                val tg = FastTileGraphics(
                    initialSize = renderable.size,
                    initialTileset = renderable.tileset,
                    initialTiles = mapOf()
                )
                renderable.render(tg)
                renderTiles(tg)
            }
        }
        lastRender = now
    }

    private fun renderTiles(graphics: FastTileGraphics) {
        for ((pos, tile) in graphics.contents()) {
            tileset.drawTile(tile, 'x', pos)
        }
    }

}
