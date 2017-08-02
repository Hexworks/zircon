package org.codetome.zircon.terminal.swing

import org.codetome.zircon.builder.DeviceConfigurationBuilder
import org.codetome.zircon.font.FontRenderer
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.builder.FontRendererBuilder
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.util.*
import javax.swing.JFrame


class SwingTerminalFrame(title: String = "ZirconTerminal",
                         terminalSize: TerminalSize,
                         deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.getDefault(),
                         fontConfiguration: FontRenderer<Graphics> = FontRendererBuilder.getDefault(),
                         private val swingTerminal: SwingTerminalComponent = SwingTerminalComponent(terminalSize, deviceConfiguration, fontConfiguration))
    : JFrame(title), Terminal by swingTerminal {

    private var disposed = false

    init {
        contentPane.layout = BorderLayout()
        contentPane.add(swingTerminal, BorderLayout.CENTER)
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        background = Color.BLACK //This will reduce white flicker when resizing the window
        pack()

        // Put input focus on the terminal component by default
        swingTerminal.requestFocusInWindow()
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
