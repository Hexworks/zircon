package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.grid.DeviceConfiguration
import java.awt.Canvas
import java.awt.Frame
import java.awt.Graphics
import java.awt.event.WindowEvent
import java.awt.event.WindowStateListener
import javax.swing.JFrame


/**
 * This class provides a application frame for a zircon grid.
 */
class SwingTileGridFrame(title: String = "ZirconTerminal",
                         size: Size,
                         deviceConfiguration: DeviceConfiguration = DeviceConfiguration.defaultConfiguration(),
                         tileset: Tileset,
                         fullScreen: Boolean,
                         private val canvas: Canvas = TerminalCanvas(),
                         private val swingTerminal: SwingTileGrid =
                                 SwingTileGrid(
                                         canvas = canvas,
                                         initialTileset = tileset,
                                         initialSize = size,
                                         deviceConfiguration = deviceConfiguration))
    : JFrame(title), InternalTileGrid by swingTerminal, WindowStateListener {

    override fun windowStateChanged(e: WindowEvent) {
        if (e.newState == Frame.NORMAL) {
            swingTerminal.flush()
        }
    }

    init {
        isResizable = false // TODO: implement proper resizing
        add(canvas)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        if (fullScreen) {
            extendedState = JFrame.MAXIMIZED_BOTH
            isUndecorated = true
        }
        pack()
        setLocationRelativeTo(null)
        canvas.createBufferStrategy(2)
        swingTerminal.initializeBufferStrategy()
        addWindowStateListener(this)
        TerminalCanvas::class.javaObjectType.cast(canvas).swingTerminal = swingTerminal
    }

    override fun close() {
        dispose()
    }

    private class TerminalCanvas : Canvas() {
        var swingTerminal: SwingTileGrid? = null

        override fun paint(g: Graphics) {
            swingTerminal?.flush()
        }
    }
}
