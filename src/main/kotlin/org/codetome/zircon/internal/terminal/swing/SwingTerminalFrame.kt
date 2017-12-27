package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Frame
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.WindowEvent
import java.awt.event.WindowStateListener
import javax.swing.JFrame


/**
 * This class provides a swing frame for a zircon terminal.
 */
class SwingTerminalFrame(title: String = "ZirconTerminal",
                         size: Size,
                         deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.DEFAULT,
                         font: Font,
                         fullScreen: Boolean,
                         private val canvas: Canvas = createCanvas(),
                         private val swingTerminal: SwingTerminal =
                         SwingTerminal(
                                 canvas = canvas,
                                 initialFont = font,
                                 initialSize = size,
                                 deviceConfiguration = deviceConfiguration))
    : JFrame(title), InternalTerminal by swingTerminal, WindowStateListener {

    override fun windowStateChanged(e: WindowEvent) {
        if(e.newState == Frame.NORMAL) {
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
    }

    override fun close() {
        dispose()
    }

    companion object {
        private fun createCanvas() = Canvas()
    }
}
