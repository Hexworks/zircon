package org.codetome.zircon.terminal.swing

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.JFrame


class SwingTerminalFrame(title: String = "ZirconTerminal",
                         size: Size,
                         deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.getDefault(),
                         font: Font<BufferedImage>,
                         private val swingTerminal: SwingTerminalCanvas = SwingTerminalCanvas(size, deviceConfiguration, font))
    : JFrame(title), Terminal by swingTerminal {

    private var disposed = false

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        add(swingTerminal)
        // isResizable = false
        background = Color.BLACK //This will reduce white flicker when resizing the window
        pack()
        setLocationRelativeTo(null)

        swingTerminal.ignoreRepaint = true
        swingTerminal.createBufferStrategy(2)
        swingTerminal.isFocusable = true

        // Put input giveFocus on the terminal component by default
        swingTerminal.requestFocusInWindow()
        swingTerminal.preferredSize = swingTerminal.preferredSize
    }

    override fun dispose() {
        super.dispose()
        disposed = true
    }

    override fun close() {
        dispose()
    }

}
