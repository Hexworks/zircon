package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.application.Renderer
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.JFrame

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(private val canvas: Canvas,
                          private val frame: JFrame,
                          private val grid: TileGrid<out Any, BufferedImage>) : Renderer {

    private val tileset = grid.tileset() as Tileset<Any, BufferedImage>

    override fun create() {
        frame.isResizable = false
        frame.addWindowStateListener {
            if (it.newState == Frame.NORMAL) {
                println("============ Window state changed")
                render()
            }
        }

        canvas.preferredSize = Dimension(
                grid.widthInPixels(),
                grid.heightInPixels())
        canvas.minimumSize = Dimension(tileset.width(), tileset.height())
        canvas.isFocusable = true
        canvas.requestFocusInWindow()

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.setLocationRelativeTo(null)
        canvas.createBufferStrategy(2)
        initializeBufferStrategy()
    }

    override fun dispose() {
        grid.close()
    }

    override fun render() {
        val bs = getBufferStrategy()
        val img = BufferedImage(
                grid.widthInPixels(),
                grid.heightInPixels(),
                BufferedImage.TRANSLUCENT)
        val gc = configureGraphics(img.graphics)
        gc.fillRect(0, 0, grid.widthInPixels(), grid.heightInPixels())

        renderTiles(gc, grid.createSnapshot(), tileset)
        grid.getLayers().forEach { layer ->
            renderTiles(
                    graphics = gc,
                    tiles = layer.createSnapshot(), // TODO: fix cat
                    tileset = layer.tileset() as Tileset<Any, BufferedImage>)
        }
        configureGraphics(getGraphics2D() ).apply {
            drawImage(img, 0, 0, null)
            dispose()
        }

        bs.show()
    }

    private fun configureGraphics(graphics: Graphics): Graphics2D {
        graphics.color = Color.BLACK
        val gc = graphics as Graphics2D
        gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
        gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
        return gc
    }

    private fun renderTiles(graphics: Graphics,
                            tiles: Map<Position, Tile<out Any>>,
                            tileset: Tileset<Any, BufferedImage>) {
        tiles.forEach { (pos, tile) ->
            val actualTile = tile as Tile<Any>
            val (x, y) = pos.toAbsolutePosition(tileset)
            val actualTileset: Tileset<Any, BufferedImage> = if (actualTile is TilesetOverride<*, *>) {
                actualTile.tileset() as Tileset<Any, BufferedImage>
            } else {
                tileset
            }

            val texture = actualTileset.fetchTextureForTile(actualTile)
            graphics.drawImage(texture.getTexture(), x, y, null)
        }
    }

    private fun getWidth() = grid.widthInPixels()

    private fun getHeight() = grid.heightInPixels()

    private tailrec fun initializeBufferStrategy() {
        val bs = canvas.bufferStrategy
        var failed = false
        try {
            bs.drawGraphics as Graphics2D
        } catch (e: NullPointerException) {
            failed = true
        }
        if (failed) {
            initializeBufferStrategy()
        } else {
            canvas.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    println("======== RESIZE?")
                }
            })
        }
    }

    private fun getBufferStrategy() = canvas.bufferStrategy

    private fun getGraphics2D() = getBufferStrategy().drawGraphics as Graphics2D
}
