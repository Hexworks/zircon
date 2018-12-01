package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.TerminalKeyListener
import org.hexworks.zircon.internal.grid.TerminalMouseListener
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader
import org.hexworks.zircon.internal.tileset.transformer.toAWTColor
import org.hexworks.zircon.platform.util.SystemUtils
import java.awt.*
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(private val canvas: Canvas,
                          private val frame: JFrame,
                          private val tileGrid: InternalTileGrid) : Renderer {

    private val config = RuntimeConfig.config
    private var firstDraw = true
    private val tilesetLoader = SwingTilesetLoader()
    private var blinkOn = true
    private var lastRender: Long = SystemUtils.getCurrentTimeMs()
    private var lastBlink: Long = lastRender

    override fun create() {
        if (RuntimeConfig.config.fullScreen) {
            frame.extendedState = JFrame.MAXIMIZED_BOTH
            frame.isUndecorated = true
        } else
            frame.setSize(tileGrid.widthInPixels, tileGrid.heightInPixels)

        frame.isVisible = true
        frame.isResizable = false
        frame.addWindowStateListener {
            if (it.newState == Frame.NORMAL) {
                render()
            }
        }

        canvas.preferredSize = Dimension(
                tileGrid.widthInPixels,
                tileGrid.heightInPixels)
        canvas.minimumSize = Dimension(tileGrid.currentTileset().width, tileGrid.currentTileset().height)
        canvas.isFocusable = true
        canvas.requestFocusInWindow()
        canvas.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.addKeyListener(TerminalKeyListener())
        val listener = object : TerminalMouseListener(
                fontWidth = tileGrid.currentTileset().width,
                fontHeight = tileGrid.currentTileset().height) {
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
                    close()
                }
            }
        }

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.setLocationRelativeTo(null)

        // buffering
        canvas.createBufferStrategy(2)
        initializeBufferStrategy()

    }

    override fun close() {
        tileGrid.close()
        frame.dispose()
    }

    override fun render() {
        val now = SystemUtils.getCurrentTimeMs()
        val bs = getBufferStrategy()
        handleFirstDraw(bs)
        handleBlink(now)
        val img = BufferedImage(
                tileGrid.widthInPixels,
                tileGrid.heightInPixels,
                BufferedImage.TRANSLUCENT)
        val gc = configureGraphics(img.graphics)
        gc.fillRect(0, 0, tileGrid.widthInPixels, tileGrid.heightInPixels)

        val snapshot = tileGrid.createSnapshot()
        tileGrid.updateAnimations(now, tileGrid)
        renderTiles(
                graphics = gc,
                snapshot = snapshot,
                tileset = tilesetLoader.loadTilesetFrom(tileGrid.currentTileset()))
        tileGrid.layers.forEach { layer ->
            renderTiles(
                    graphics = gc,
                    snapshot = layer.createSnapshot(),
                    tileset = tilesetLoader.loadTilesetFrom(layer.currentTileset()))
        }
        if (shouldDrawCursor()) {
            tileGrid.getTileAt(tileGrid.cursorPosition()).map {
                drawCursor(gc, it, tileGrid.cursorPosition())
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

    private fun renderTiles(graphics: Graphics2D,
                            snapshot: Snapshot,
                            tileset: Tileset<Graphics2D>) {
        snapshot.cells.forEach { (pos, tile) ->
            if (tile !== Tile.empty()) {
                val actualTile = if (tile.isBlinking() && blinkOn) {
                    tile.withBackgroundColor(tile.foregroundColor)
                            .withForegroundColor(tile.backgroundColor)
                } else {
                    tile
                }
                val actualTileset: Tileset<Graphics2D> = if (actualTile is TilesetOverride) {
                    tilesetLoader.loadTilesetFrom(actualTile.currentTileset())
                } else {
                    tileset
                }

                actualTileset.drawTile(
                        tile = actualTile,
                        surface = graphics,
                        position = pos)
            }
        }
    }

    private fun drawCursor(graphics: Graphics, character: Tile, position: Position) {
        val tileWidth = tileGrid.currentTileset().width
        val tileHeight = tileGrid.currentTileset().height
        val x = position.x * tileWidth
        val y = position.y * tileHeight
        val cursorColor = config.cursorColor.toAWTColor()
        graphics.color = cursorColor
        when (config.cursorStyle) {
            CursorStyle.USE_CHARACTER_FOREGROUND -> {
                if (blinkOn) {
                    graphics.color = character.foregroundColor.toAWTColor()
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

    private fun getWidth() = tileGrid.widthInPixels

    private fun getHeight() = tileGrid.heightInPixels

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

}
