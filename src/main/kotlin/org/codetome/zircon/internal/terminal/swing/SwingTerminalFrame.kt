package org.codetome.zircon.internal.terminal.swing

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import java.awt.Canvas
import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.JFrame

/**
 * This class provides a swing frame for a zircon terminal.
 */
class SwingTerminalFrame(title: String = "ZirconTerminal",
                         size: Size,
                         deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.getDefault(),
                         font: Font<BufferedImage>,
                         fullScreen: Boolean,
                         private val canvas: Canvas = createCanvas(),
                         private val swingTerminal: SwingTerminal =
                         SwingTerminal(
                                 canvas = canvas,
                                 font = font,
                                 initialSize = size,
                                 deviceConfiguration = deviceConfiguration))
    : JFrame(title), Terminal by swingTerminal {

    init {
        add(canvas)
        canvas.preferredSize = swingTerminal.getPreferredSize()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        if(fullScreen) {
            extendedState = JFrame.MAXIMIZED_BOTH
            isUndecorated = true
        }
        pack()
        setLocationRelativeTo(null)
        canvas.ignoreRepaint = true
        canvas.createBufferStrategy(2)
        canvas.isFocusable = true
        canvas.requestFocusInWindow()
    }

    override fun dispose() {
        super.dispose()
    }

    override fun close() {
        dispose()
    }

    companion object {
        private fun createCanvas() = Canvas()
    }
}
