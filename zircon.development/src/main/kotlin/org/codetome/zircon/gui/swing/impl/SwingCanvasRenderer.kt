package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.application.Renderer
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.gui.swing.grid.TerminalKeyListener
import org.codetome.zircon.gui.swing.grid.TerminalMouseListener
import org.codetome.zircon.internal.config.RuntimeConfig
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.JFrame

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(private val canvas: Canvas,
                          private val frame: JFrame,
                          private val tileGrid: TileGrid) : Renderer {

    private var firstDraw = true
    private val tilesetLoader = SwingTilesetLoader()

    override fun create() {
        frame.isResizable = false
        frame.addWindowStateListener {
            if (it.newState == Frame.NORMAL) {
                println("============ Window state changed")
                render()
            }
        }

        canvas.preferredSize = Dimension(
                tileGrid.widthInPixels(),
                tileGrid.heightInPixels())
        canvas.minimumSize = Dimension(tileGrid.tileset().width, tileGrid.tileset().height)
        canvas.isFocusable = true
        canvas.requestFocusInWindow()
        canvas.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.addKeyListener(TerminalKeyListener())
        val listener = object : TerminalMouseListener(
                fontWidth = tileGrid.tileset().width,
                fontHeight = tileGrid.tileset().height) {
            override fun mouseClicked(e: MouseEvent) {
                super.mouseClicked(e)
                canvas.requestFocusInWindow()
            }
        }
        canvas.addMouseListener(listener)
        canvas.addMouseMotionListener(listener)
        canvas.addMouseWheelListener(listener)
        canvas.addHierarchyListener { e ->
            if (e.changeFlags == HierarchyEvent.DISPLAYABILITY_CHANGED.toLong()) {
                if (e.changed.isDisplayable) {
                    // no op
                } else {
                    dispose()
                }
            }
        }

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        if (RuntimeConfig.config.fullScreen) {
            frame.extendedState = JFrame.MAXIMIZED_BOTH
            frame.isUndecorated = true
        }
        frame.pack()
        frame.setLocationRelativeTo(null)

        // buffering
        canvas.createBufferStrategy(2)
        initializeBufferStrategy()

    }

    override fun dispose() {
        tileGrid.close()
    }

    override fun render() {
        val bs = getBufferStrategy()
        if (firstDraw) {
            firstDraw = false
            bs.drawGraphics.color = Color.BLACK
            bs.drawGraphics.fillRect(0, 0, getWidth(), getHeight())
            bs.show()
        }
        val img = BufferedImage(
                tileGrid.widthInPixels(),
                tileGrid.heightInPixels(),
                BufferedImage.TRANSLUCENT)
        val gc = configureGraphics(img.graphics)
        gc.fillRect(0, 0, tileGrid.widthInPixels(), tileGrid.heightInPixels())

        renderTiles(gc, tileGrid.createSnapshot(), tilesetLoader.loadTilesetFrom(tileGrid.tileset()))
        tileGrid.getLayers().forEach { layer ->
            renderTiles(
                    graphics = gc,
                    tiles = layer.createSnapshot(),
                    tileset = tilesetLoader.loadTilesetFrom(layer.tileset()))
        }
        configureGraphics(getGraphics2D()).apply {
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
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
        return gc
    }

    private fun renderTiles(graphics: Graphics,
                            tiles: Map<Position, Tile>,
                            tileset: Tileset<out Tile, BufferedImage>) {
        tiles.forEach { (pos, tile) ->
            val (x, y) = pos.toAbsolutePosition(tileset)
            val actualTileset: Tileset<Tile, BufferedImage> = if (tile is TilesetOverride) {
                tile.tileset() as Tileset<Tile, BufferedImage>
            } else {
                tileset as Tileset<Tile, BufferedImage>
            }

            val texture = actualTileset.fetchTextureForTile(tile)
            graphics.drawImage(texture.getTexture(), x, y, null)
        }
    }

    private fun getWidth() = tileGrid.widthInPixels()

    private fun getHeight() = tileGrid.heightInPixels()

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
