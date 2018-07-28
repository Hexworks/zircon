package org.codetome.zircon.poc.drawableupgrade.renderer

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.drawables.TilesetOverride
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import java.util.*

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(override val surface: Canvas,
                          private val grid: TileGrid<out Any, BufferedImage>) : Renderer<Canvas> {

    private var firstDraw = true
    private val tileset = grid.tileset() as Tileset<Any, BufferedImage>

    init {
        surface.preferredSize = Dimension(
                tileset.getWidth() * grid.getColumnCount(),
                tileset.getHeight() * grid.getRowCount())
        surface.minimumSize = Dimension(tileset.getWidth(), tileset.getHeight())
        surface.isFocusable = true
        surface.requestFocusInWindow()

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
        val tiles = grid.createSnapshot().toSortedMap() as SortedMap<GridPosition, Tile<Any>>
        renderTiles(tiles, tileset, GridPosition(0, 0))
        grid.getLayers().forEach { layer ->
            renderTiles(layer.createSnapshot().toSortedMap() as SortedMap<GridPosition, Tile<Any>>, layer.tileset() as Tileset<Any, BufferedImage>, layer.position())
        }
        fillLeftoverSpaceWithBlack()
        gc.dispose()
        bs.show()
    }

    private fun renderTiles(tiles: SortedMap<GridPosition, Tile<Any>>,
                            tileset: Tileset<Any, BufferedImage>,
                            offset: GridPosition) {
        tiles.forEach { pos, tile ->
            val (gridX, gridY) = pos + offset
            val actualTileset: Tileset<Any, BufferedImage> = if (tile is TilesetOverride<*, *>) {
                tile.tileset() as Tileset<Any, BufferedImage>
            } else {
                tileset
            }

            val texture = actualTileset.fetchTextureForTile(tile)
            val x = gridX * texture.getWidth()
            val y = gridY * texture.getHeight()
            getGraphics2D().drawImage(texture.getTexture(), x, y, null)
        }
    }

    private fun getWidth() = tileset.getWidth() * grid.getColumnCount()

    private fun getHeight() = tileset.getHeight() * grid.getRowCount()

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

        val leftoverWidth = getWidth() % tileset.getWidth()
        if (leftoverWidth > 0) {
            graphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % tileset.getHeight()
        if (leftoverHeight > 0) {
            graphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    private fun getBufferStrategy() = surface.bufferStrategy

    private fun getGraphics2D() = getBufferStrategy().drawGraphics as Graphics2D
}
