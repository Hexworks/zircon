package org.codetome.zircon.gui.swing.application

import org.codetome.zircon.api.application.Renderer
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.CursorStyle
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.gui.swing.grid.TerminalKeyListener
import org.codetome.zircon.gui.swing.grid.TerminalMouseListener
import org.codetome.zircon.gui.swing.impl.SwingTilesetLoader
import org.codetome.zircon.gui.swing.tileset.transformer.toAWTColor
import org.codetome.zircon.internal.config.RuntimeConfig
import org.codetome.zircon.platform.util.SystemUtils
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(private val canvas: Canvas,
                          private val frame: JFrame,
                          private val tileGrid: TileGrid) : Renderer {

    val config = RuntimeConfig.config
    private var firstDraw = true
    private val tilesetLoader = SwingTilesetLoader()
    private var hasBlinkingText = config().isCursorBlinking
    private var blinkOn = true
    private var lastRender: Long = SystemUtils.getCurrentTimeMs()
    private var lastBlink: Long = lastRender

    override fun create() {
        frame.isResizable = false
        frame.addWindowStateListener {
            if (it.newState == Frame.NORMAL) {
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
        val now = SystemUtils.getCurrentTimeMs()
        val bs = getBufferStrategy()
        handleFirstDraw(bs)
        handleBlink(now)
        val img = BufferedImage(
                tileGrid.widthInPixels(),
                tileGrid.heightInPixels(),
                BufferedImage.TRANSLUCENT)
        val gc = configureGraphics(img.graphics)
        gc.fillRect(0, 0, tileGrid.widthInPixels(), tileGrid.heightInPixels())

        renderTiles(
                graphics = gc,
                tiles = tileGrid.createSnapshot(),
                tileset = tilesetLoader.loadTilesetFrom(tileGrid.tileset()))
        tileGrid.getLayers().forEach { layer ->
            renderTiles(
                    graphics = gc,
                    tiles = layer.createSnapshot(),
                    tileset = tilesetLoader.loadTilesetFrom(layer.tileset()))
        }
        if (shouldDrawCursor()) {
            tileGrid.getTileAt(tileGrid.getCursorPosition()).map {
                drawCursor(gc, it, tileGrid.getCursorPosition())
            }
        }

        configureGraphics(getGraphics2D()).apply {
            drawImage(img, 0, 0, null)
            dispose()
        }

        bs.show()
        lastRender = now
    }

    private fun handleBlink(now: Long) {
        if (now > lastBlink + config.blinkLengthInMilliSeconds) {
            blinkOn = blinkOn.not()
            lastBlink = now
        }
    }

    private fun handleFirstDraw(bs: BufferStrategy) {
        if (firstDraw) {
            firstDraw = false
            bs.drawGraphics.color = Color.BLACK
            bs.drawGraphics.fillRect(0, 0, getWidth(), getHeight())
            bs.show()
        }
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
            if (tile !== Tile.empty()) {
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
    }

    private fun drawCursor(graphics: Graphics, character: Tile, position: Position) {
        val tileWidth = tileGrid.tileset().width
        val tileHeight = tileGrid.tileset().height
        val x = position.x * tileWidth
        val y = position.y * tileHeight
        val cursorColor = config.cursorColor.toAWTColor()
        graphics.color = cursorColor
        when (config.cursorStyle) {
            CursorStyle.USE_CHARACTER_FOREGROUND -> {
                if (blinkOn) {
                    graphics.color = character.getForegroundColor().toAWTColor()
                    graphics.fillRect(x, y, tileWidth, tileHeight)
                }
            }
            CursorStyle.FIXED_BACKGROUND -> graphics.fillRect(x, y, tileWidth, tileHeight)
            CursorStyle.UNDER_BAR -> graphics.fillRect(x, y + tileHeight - 3, tileWidth, 2)
            CursorStyle.VERTICAL_BAR -> graphics.fillRect(x, y + 1, 2, tileHeight - 2)
        }
    }

    private fun shouldDrawCursor(): Boolean {
        return tileGrid.isCursorVisible() &&
                (config.isCursorBlinking.not() || config.isCursorBlinking && blinkOn)
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
        }
    }

    private fun getBufferStrategy() = canvas.bufferStrategy

    private fun getGraphics2D() = getBufferStrategy().drawGraphics as Graphics2D

    private fun config() = RuntimeConfig.config
}
