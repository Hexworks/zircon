package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.CloseBehavior
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader
import org.hexworks.zircon.internal.tileset.transformer.toAWTColor
import org.hexworks.zircon.internal.uievent.KeyboardEventListener
import org.hexworks.zircon.internal.uievent.MouseEventListener
import org.hexworks.zircon.platform.util.SystemUtils
import java.awt.*
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import javax.swing.JFrame


@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer(
        private val canvas: Canvas,
        private val frame: JFrame,
        private val tileGrid: InternalTileGrid,
        private val config: AppConfig,
        private val app: Application
) : Renderer {

    override val isClosed = false.toProperty()

    private val tilesetLoader = SwingTilesetLoader()
    private var blinkOn = true
    private var lastRender: Long = SystemUtils.getCurrentTimeMs()
    private var lastBlink: Long = lastRender

    private val keyboardEventListener = KeyboardEventListener()
    private val mouseEventListener = object : MouseEventListener(
            fontWidth = tileGrid.tileset.width,
            fontHeight = tileGrid.tileset.height) {
        override fun mouseClicked(e: MouseEvent) {
            super.mouseClicked(e)
            canvas.requestFocusInWindow()
        }
    }

    private val parallelism = 4
    private val tilesToRender = mutableListOf<MutableMap<Position, MutableList<Pair<Tile, TilesetResource>>>>().apply {
        (0..parallelism).forEach { _ -> add(mutableMapOf()) }
    }
    private val interval = tileGrid.width / (parallelism - 1)
    private val startBarrier = CyclicBarrier(parallelism + 1) {
        val renderables = tileGrid.renderables
        for (i in renderables.indices) {
            val renderable = renderables[i]
            if (!renderable.isHidden) {
                val graphics = FastTileGraphics(
                        initialSize = renderable.size,
                        initialTileset = renderable.tileset,
                        initialTiles = emptyMap()
                )
                renderable.render(graphics)
                graphics.contents().forEach { (tilePos, tile) ->
                    val finalPos = tilePos + renderable.position
                    val idx = finalPos.x / interval
                    tilesToRender[idx].getOrPut(finalPos) { mutableListOf() }
                    if (tile.isOpaque) {
                        tilesToRender[idx][finalPos] = mutableListOf(tile to renderable.tileset)
                    } else {
                        tilesToRender[idx][finalPos]?.add(tile to renderable.tileset)
                    }
                }
            }
        }
    }
    private val finishBarrier = CyclicBarrier(parallelism + 1) {
        tilesToRender.forEach { it.clear() }
    }
    private val workers = Executors.newFixedThreadPool(parallelism).apply {
        for (i in 0..parallelism) {
            submit {
                while (isClosed.value.not()) {
                    try {
                        startBarrier.await()
                        canvas.bufferStrategy.drawGraphics.configure().apply {
                            if (shouldDrawCursor()) {
                                tileGrid.getTileAt(tileGrid.cursorPosition).map {
                                    drawCursor(this, it, tileGrid.cursorPosition)
                                }
                            }
                            color = Color.BLACK
                            fillRect(0, 0, tileGrid.widthInPixels, tileGrid.heightInPixels)
                            renderPart(this, tilesToRender[i])
                            dispose()
                        }
                    } finally {
                        finishBarrier.await()
                    }
                }
            }
        }
    }

    override fun create() {
        if (config.fullScreen) {
            frame.extendedState = JFrame.MAXIMIZED_BOTH
        }
        if (config.borderless) {
            frame.isUndecorated = true
        }

        frame.isResizable = false
        frame.addWindowStateListener {
            if (it.newState == Frame.NORMAL) {
                render()
            }
        }

        canvas.preferredSize = Dimension(
                tileGrid.widthInPixels,
                tileGrid.heightInPixels)
        canvas.minimumSize = Dimension(tileGrid.tileset.width, tileGrid.tileset.height)
        canvas.isFocusable = true
        canvas.requestFocusInWindow()
        canvas.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.addKeyListener(keyboardEventListener)
        canvas.addMouseListener(mouseEventListener)
        canvas.addMouseMotionListener(mouseEventListener)
        canvas.addMouseWheelListener(mouseEventListener)
        canvas.addHierarchyListener { e ->
            if (e.changeFlags == HierarchyEvent.DISPLAYABILITY_CHANGED.toLong()) {
                if (!e.changed.isDisplayable) {
                    close()
                }
            }
        }

        frame.defaultCloseOperation = when (config.closeBehavior) {
            CloseBehavior.DO_NOTHING_ON_CLOSE -> JFrame.DO_NOTHING_ON_CLOSE
            CloseBehavior.EXIT_ON_CLOSE -> JFrame.EXIT_ON_CLOSE
        }

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(windowEvent: WindowEvent?) {
                app.stop()
            }
        })
        frame.pack()
        frame.isVisible = true
        frame.setLocationRelativeTo(null)

        // buffering
        canvas.createBufferStrategy(2)
        initializeBufferStrategy()
    }

    override fun close() {
        if (!isClosed.value) {
            isClosed.value = true
            tileGrid.close()
            frame.dispose()
        }
    }

    override fun render() {
        val now = SystemUtils.getCurrentTimeMs()

        keyboardEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
        mouseEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
        tileGrid.updateAnimations(now, tileGrid)

        val bs: BufferStrategy = canvas.bufferStrategy // this is a regular Swing Canvas object
        handleBlink(now)

        // we start the rendering by releasing waiting workers
        startBarrier.await()
        // then await their completion
        finishBarrier.await()

        bs.show()
        lastRender = now
    }

    private fun renderPart(
            graphics: Graphics2D,
            tilesToRender: MutableMap<Position, MutableList<Pair<Tile, TilesetResource>>>
    ) {
        val img = BufferedImage(
                tileGrid.widthInPixels,
                tileGrid.heightInPixels,
                BufferedImage.TRANSLUCENT
        )
        val gc = img.graphics.configure()
        for ((pos, tiles) in tilesToRender) {
            for ((tile, tileset) in tiles) {
                renderTile(
                        graphics = gc,
                        position = pos,
                        tile = tile,
                        tileset = tilesetLoader.loadTilesetFrom(tileset)
                )
            }
        }
        graphics.drawImage(img, 0, 0, null)
    }

    private fun Graphics.configure(): Graphics2D {
        this.color = Color.BLACK
        val gc = this as Graphics2D
//        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
//        gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
//        gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
//        gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
//        gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
//        gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
//        gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        return gc
    }

    private fun renderTile(
            graphics: Graphics2D,
            position: Position,
            tile: Tile,
            tileset: Tileset<Graphics2D>
    ) {
        if (tile.isNotEmpty) {
            var finalTile = tile
            finalTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    (finalTile as? CharacterTile)?.let {
                        finalTile = modifier.transform(it)
                    }
                }
            }
            finalTile = if (tile.isBlinking && blinkOn) {
                tile.withBackgroundColor(tile.foregroundColor)
                        .withForegroundColor(tile.backgroundColor)
            } else {
                tile
            }
            ((finalTile as? TilesetHolder)?.let {
                tilesetLoader.loadTilesetFrom(it.tileset)
            } ?: tileset).drawTile(
                    tile = finalTile,
                    surface = graphics,
                    position = position
            )
        }
    }

    private fun handleBlink(now: Long) {
        if (now > lastBlink + config.blinkLengthInMilliSeconds) {
            blinkOn = !blinkOn
            lastBlink = now
        }
    }

    private fun drawCursor(graphics: Graphics, character: Tile, position: Position) {
        val tileWidth = tileGrid.tileset.width
        val tileHeight = tileGrid.tileset.height
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
        return tileGrid.isCursorVisible &&
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

}
