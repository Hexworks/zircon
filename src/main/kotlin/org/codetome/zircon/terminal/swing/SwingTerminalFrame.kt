package org.codetome.zircon.terminal.swing

import org.codetome.zircon.api.DeviceConfigurationBuilder
import org.codetome.zircon.font.Font
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JFrame


class SwingTerminalFrame(title: String = "ZirconTerminal",
                         size: Size,
                         deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.getDefault(),
                         font: Font<BufferedImage>,
                         private val swingTerminal: SwingTerminalCanvas = SwingTerminalCanvas(size, deviceConfiguration, font))
    : JFrame(title), VirtualTerminal by swingTerminal {

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

        // Put input focus on the terminal component by default
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

    override fun pollInput(): Optional<Input> {
        if (disposed) {
            return Optional.of(KeyStroke(it = InputType.EOF))
        }
        return swingTerminal.pollInput()
    }

}
