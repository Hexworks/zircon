package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.CloseBehavior
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer
import org.hexworks.zircon.internal.uievent.KeyboardEventListener
import org.hexworks.zircon.internal.uievent.MouseEventListener
import java.awt.*
import java.awt.event.HierarchyEvent
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferStrategy
import javax.swing.JFrame

@Suppress("UNCHECKED_CAST")
class SwingCanvasRenderer private constructor(
    tileGrid: InternalTileGrid,
    tilesetLoader: TilesetLoader<Graphics2D>,
    val canvas: Canvas,
    val frame: JFrame,
    val shouldInitializeSwingComponents: Boolean
) : BaseRenderer<Graphics2D>(tileGrid, tilesetLoader), SwingRenderer,
    Observable<SwingCanvasRenderer> by DefaultObservable() {

    private val config = tileGrid.config
    private val keyboardEventListener = KeyboardEventListener()
    private val mouseEventListener = object : MouseEventListener(
        fontWidth = tileGrid.tileset.width,
        fontHeight = tileGrid.tileset.height
    ) {
        override fun mouseClicked(e: MouseEvent) {
            super.mouseClicked(e)
            canvas.requestFocusInWindow()
        }
    }

    /**
     * Adds a callback [fn] that will be called whenever the frame where the contents
     * of the [tileGrid] are rendered is closed.
     */
    override fun onFrameClosed(fn: (SwingRenderer) -> Unit): Subscription {
        return addObserver(fn)
    }

    override fun create() {
        if (closed.not()) {
            // display settings
            if (config.fullScreen) {
                frame.extendedState = JFrame.MAXIMIZED_BOTH
            }
            if (config.borderless) {
                frame.isUndecorated = true
            }

            // no resize
            frame.isResizable = false

            // rendering
            frame.addWindowStateListener {
                if (it.newState == Frame.NORMAL) {
                    render()
                }
            }

            // dimensions
            canvas.preferredSize = Dimension(
                tileGrid.widthInPixels,
                tileGrid.heightInPixels
            )
            canvas.minimumSize = Dimension(tileGrid.tileset.width, tileGrid.tileset.height)

            // input listeners
            canvas.addKeyListener(keyboardEventListener)
            canvas.addMouseListener(mouseEventListener)
            canvas.addMouseMotionListener(mouseEventListener)
            canvas.addMouseWheelListener(mouseEventListener)

            // window closed
            canvas.addHierarchyListener { e ->
                if (e.changeFlags == HierarchyEvent.DISPLAYABILITY_CHANGED.toLong()) {
                    if (!e.changed.isDisplayable) {
                        close()
                    }
                }
            }

            // close behavior
            frame.defaultCloseOperation = when (config.closeBehavior) {
                CloseBehavior.DO_NOTHING_ON_CLOSE -> JFrame.DO_NOTHING_ON_CLOSE
                CloseBehavior.EXIT_ON_CLOSE -> JFrame.EXIT_ON_CLOSE
            }

            // app stop callback
            frame.addWindowListener(object : WindowAdapter() {
                override fun windowClosing(windowEvent: WindowEvent?) {
                    notifyObservers(this@SwingCanvasRenderer)
                }
            })

            // focus settings
            canvas.isFocusable = true

            if (shouldInitializeSwingComponents) {
                initializeSwingComponents()
            }

            // buffering
            canvas.createBufferStrategy(2)
            initializeBufferStrategy()
        }
    }

    protected override fun doRender(now: Long) {
        val bs: BufferStrategy = canvas.bufferStrategy // this is a regular Swing Canvas object

        canvas.bufferStrategy.drawGraphics.configure().apply {
            color = Color.BLACK
            fillRect(0, 0, tileGrid.widthInPixels, tileGrid.heightInPixels)
            drawTiles(this)
            dispose()
        }

        bs.show()
    }

    override fun doClose() {
        frame.dispose()
    }

    private fun Graphics.configure(): Graphics2D {
        val gc = this as Graphics2D
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)
        gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
        gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
        gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        return gc
    }

    override fun processInputEvents() {
        keyboardEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
        mouseEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
    }

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

    private fun initializeSwingComponents() {
        canvas.requestFocusInWindow()
        canvas.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        canvas.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet<AWTKeyStroke>())
        frame.pack()
        frame.isVisible = true
        frame.setLocationRelativeTo(null)
    }

    companion object {

        /**
         * Creates a [SwingCanvasRenderer].
         */
        internal fun create(
            tileGrid: InternalTileGrid,
            tilesetLoader: TilesetLoader<Graphics2D>,
            /**
             * The grid will be drawn on this [Canvas]. Use this parameter
             * if you want to provide your own.
             */
            canvas: Canvas,
            /**
             * The [JFrame] that will display the [canvas]. Use this parameter
             * if you want to provide your own.
             */
            frame: JFrame,
            /**
             * If set to `false` initializations won't be run on [canvas] and [frame].
             * This includes disabling focus traversal (between Swing components) and
             * displaying and packing the [frame] itself.
             * Set this to `false` if you want to handle these yourself. A typical example
             * of this would be the case when you're using multiple [Application]s.
             */
            shouldInitializeSwingComponents: Boolean
        ): SwingCanvasRenderer = SwingCanvasRenderer(
            tileGrid = tileGrid,
            tilesetLoader = tilesetLoader,
            canvas = canvas,
            frame = frame,
            shouldInitializeSwingComponents = shouldInitializeSwingComponents
        )
    }
}
