package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.interop.toAWTColor
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.grid.CursorStyle
import org.codetome.zircon.api.grid.DeviceConfiguration
import org.codetome.zircon.internal.grid.application.ApplicationTileGrid
import org.codetome.zircon.internal.grid.virtual.VirtualTileGrid
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.SwingUtilities

/**
 * Concrete implementation of [ApplicationTileGrid] that adapts it to Swing
 * and wraps a [Canvas].
 */
class SwingTileGrid(
        initialTileset: Tileset,
        initialSize: Size,
        private val canvas: Canvas,
        private val deviceConfiguration: DeviceConfiguration)

    : ApplicationTileGrid(
        deviceConfiguration = deviceConfiguration,
        terminal = VirtualTileGrid(
                initialSize = initialSize,
                initialTileset = initialTileset)) {

    private var firstDraw = true

    init {
        //Prevent us from shrinking beyond one character
        canvas.preferredSize = Dimension(
                getSupportedFontSize().xLength * getBoundableSize().xLength,
                getSupportedFontSize().yLength * getBoundableSize().yLength)
        canvas.isFocusable = true
        canvas.requestFocusInWindow()
        canvas.minimumSize = Dimension(initialTileset.getWidth(), initialTileset.getHeight())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.addKeyListener(TerminalKeyListener(
                deviceConfiguration = deviceConfiguration))
        val listener = object : TerminalMouseListener(
                deviceConfiguration = deviceConfiguration,
                fontWidth = getSupportedFontSize().xLength,
                fontHeight = getSupportedFontSize().yLength) {
            override fun mouseClicked(e: MouseEvent) {
                super.mouseClicked(e)
                this@SwingTileGrid.canvas.requestFocusInWindow()
            }
        }
        canvas.addMouseListener(listener)
        canvas.addMouseMotionListener(listener)
        canvas.addMouseWheelListener(listener)
        canvas.addHierarchyListener { e ->
            if (e.changeFlags == HierarchyEvent.DISPLAYABILITY_CHANGED.toLong()) {
                if (e.changed.isDisplayable) {
                    doCreate()
                } else {
                    doDispose()
                }
            }
        }
    }

    override fun getHeight() = canvas.height

    override fun getWidth() = canvas.width

    @Synchronized
    override fun flush() {
        if (SwingUtilities.isEventDispatchThread()) {
            drawAndDispose()
        } else {
            SwingUtilities.invokeLater {
                drawAndDispose()
            }
        }
    }

    override fun doResize(width: Int, height: Int) {
        super.doResize(width, height)
        fillLeftoverSpaceWithBlack()
        flush()
    }

    override fun drawFontTextureRegion(tileTexture: TileTexture<*>, x: Int, y: Int) {
        getGraphics2D().drawImage(tileTexture.getBackend() as BufferedImage, x, y, null)
    }

    override fun drawCursor(character: Tile, x: Int, y: Int) {
        val graphics = getGraphics2D()
        graphics.color = deviceConfiguration.cursorColor.toAWTColor()
        when (deviceConfiguration.cursorStyle) {
            CursorStyle.USE_CHARACTER_FOREGROUND -> {
                graphics.color = character.getForegroundColor().toAWTColor()
                graphics.fillRect(x, y, getSupportedFontSize().xLength, getSupportedFontSize().yLength)
            }
            CursorStyle.FIXED_BACKGROUND -> graphics.fillRect(x, y, getSupportedFontSize().xLength, getSupportedFontSize().yLength)
            CursorStyle.UNDER_BAR -> graphics.fillRect(x, y + getSupportedFontSize().yLength - 3, getSupportedFontSize().xLength, 2)
            CursorStyle.VERTICAL_BAR -> graphics.fillRect(x, y + 1, 2, getSupportedFontSize().yLength - 2)
        }
    }

    tailrec fun initializeBufferStrategy() {
        val bs = canvas.bufferStrategy
        var failed = false
        try {
            bs.drawGraphics as Graphics2D
        } catch (e: NullPointerException) {
            // buffer strategy might not be initialized yet
            failed = true
        }
        if(failed) {
            initializeBufferStrategy()
        } else {
            canvas.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    doResize(e.component.width, e.component.height)
                }
            })
        }
    }

    private fun drawAndDispose() {
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
        doRender()
        gc.dispose()
        bs.show()
    }

    private fun fillLeftoverSpaceWithBlack() {
        val graphics = getGraphics2D()
        // Take care of the left-over area at the bottom and right of the component where no character can fit
        graphics.color = Color.BLACK

        val leftoverWidth = getWidth() % getSupportedFontSize().xLength
        if (leftoverWidth > 0) {
            graphics.fillRect(getWidth() - leftoverWidth, 0, leftoverWidth, getHeight())
        }

        val leftoverHeight = getHeight() % getSupportedFontSize().yLength
        if (leftoverHeight > 0) {
            graphics.fillRect(0, getHeight() - leftoverHeight, getWidth(), leftoverHeight)
        }
    }

    private fun getBufferStrategy() = canvas.bufferStrategy

    private fun getGraphics2D() = getBufferStrategy().drawGraphics as Graphics2D
}
