package org.codetome.zircon.terminal.swing

import org.codetome.zircon.font.MonospaceFontRenderer
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.config.ColorConfiguration
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.config.FontConfiguration
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.util.*
import javax.swing.JFrame


class SwingTerminalFrame(title: String = "ZirconTerminal",
                         terminalSize: TerminalSize,
                         deviceConfiguration: DeviceConfiguration = DeviceConfiguration.getDefault(),
                         fontConfiguration: MonospaceFontRenderer<Graphics> = FontConfiguration.getDefault(),
                         colorConfiguration: ColorConfiguration = ColorConfiguration.getDefault(),
                         private val swingTerminal: SwingTerminalComponent = SwingTerminalComponent(terminalSize, deviceConfiguration, fontConfiguration, colorConfiguration))
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
