package org.codetome.zircon.swing

import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.AbsolutePosition
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Renderer
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(val surface: Canvas,
                          private val grid: TileGrid<out Any, BufferedImage>) : Renderer {

    private var firstDraw = true
    private val tileset = grid.tileset() as Tileset<Any, BufferedImage>

    init {
        surface.preferredSize = Dimension(
                grid.widthInPixels(),
                grid.heightInPixels())
        surface.minimumSize = Dimension(tileset.width(), tileset.height())
        surface.isFocusable = true
        surface.requestFocusInWindow()

    }

    override fun create() {
        TODO("not implemented")
    }

    override fun dispose() {
        TODO("not implemented")
    }

    override fun render() {
        val bs = getBufferStrategy()
        val gc = getGraphics2D()
        gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
        gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
        if (firstDraw) {
            firstDraw = false
            gc.color = Color.BLACK
            gc.fillRect(0, 0, getWidth(), getHeight())
            bs.show()
        }
        renderTiles(grid.createSnapshot(), tileset, AbsolutePosition(0, 0))
        grid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.createSnapshot(), // TODO: fix cat
                    tileset = layer.tileset() as Tileset<Any, BufferedImage>,
                    offset = layer.position().toAbsolutePosition(tileset))
        }
        fillLeftoverSpaceWithBlack()
        gc.dispose()
        bs.show()
    }

    private fun renderTiles(tiles: Map<Position, Tile<out Any>>,
                            tileset: Tileset<Any, BufferedImage>,
                            offset: AbsolutePosition) {
        tiles.forEach { (pos, tile) ->
            val actualTile = tile as Tile<Any>
            val (x, y) = pos.toAbsolutePosition(tileset) + offset
            val actualTileset: Tileset<Any, BufferedImage> = if (actualTile is TilesetOverride<*, *>) {
                actualTile.tileset() as Tileset<Any, BufferedImage>
            } else {
                tileset
            }

            val texture = actualTileset.fetchTextureForTile(actualTile)
            getGraphics2D().drawImage(texture.getTexture(), x, y, null)
        }
    }

    private fun getWidth() = grid.widthInPixels()

    private fun getHeight() = grid.heightInPixels()

    tailrec fun initializeBufferStrategy() {
        val bs = surface.bufferStrategy
        var failed = false
        try {
            bs.drawGraphics as Graphics2D
        } catch (e: NullPointerException) {
            failed = true
        }
        if (failed) {
            initializeBufferStrategy()
        } else {
            surface.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    println("======== RESIZE?")
                }
            })
        }
    }

    private fun fillLeftoverSpaceWithBlack() {
        val graphics = getGraphics2D()
        // Take care of the left-over area at the bottom and right of the component where no character can fit
        graphics.color = Color.BLACK

        val leftoverWidth = getWidth() % tileset.width()
        if (leftoverWidth > 0) {
            graphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % tileset.height()
        if (leftoverHeight > 0) {
            graphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    private fun getBufferStrategy() = surface.bufferStrategy

    private fun getGraphics2D() = getBufferStrategy().drawGraphics as Graphics2D
}
