package org.codetome.zircon.terminal

import org.codetome.zircon.font.MonospaceFontRenderer
import org.codetome.zircon.screen.TerminalScreen
import org.codetome.zircon.terminal.config.ColorConfiguration
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.config.FontConfiguration
import org.codetome.zircon.terminal.swing.SwingTerminalComponent
import org.codetome.zircon.terminal.swing.SwingTerminalFrame
import java.awt.Graphics


class DefaultTerminalFactory
    : TerminalFactory {

    private var initialTerminalSize: TerminalSize = TerminalSize.DEFAULT
    private var title: String = "Zircon Terminal"
    private var autoOpenTerminalFrame = true
    private var colorConfiguration = ColorConfiguration.getDefault()
    private var deviceConfiguration = DeviceConfiguration.getDefault()
    private var fontRenderer: MonospaceFontRenderer<Graphics> = FontConfiguration.createSwingFontRendererForPhysicalFonts()

    override fun createTerminal(): Terminal = createTerminalEmulator()

    fun createTerminalEmulator() = createSwingTerminal()

    fun createSwingTerminal(): SwingTerminalFrame {
        val stf = SwingTerminalFrame(
                title = title,
                terminalSize =  initialTerminalSize,
                deviceConfiguration = deviceConfiguration,
                fontConfiguration = fontRenderer,
                colorConfiguration = colorConfiguration)
        if(autoOpenTerminalFrame) {
            stf.isVisible = true
        }
        return stf
    }

    /**
     * Sets a hint to the [TerminalFactory] of what size to use when creating the terminal.
     * Most terminals are not created on request but for example the [SwingTerminalComponent]
     * and [SwingTerminalFrame] are and this value will be passed down on
     * creation.
     */
    fun setInitialTerminalSize(initialTerminalSize: TerminalSize): DefaultTerminalFactory {
        this.initialTerminalSize = initialTerminalSize
        return this
    }

    /**
     * Controls whether a [SwingTerminalFrame] shall be automatically shown (.setVisible(true))
     * immediately after creation.
     * If `false`, you will manually need to call `.setVisible(true)` on the JFrame to actually
     * see the terminal window. Default for this value is `true`.
     */
    fun setAutoOpenTerminalWindow(autoOpenTerminalFrame: Boolean): DefaultTerminalFactory {
        this.autoOpenTerminalFrame = autoOpenTerminalFrame
        return this
    }

    /**
     * Sets the title to use on created [SwingTerminalFrame]s created by this factory
     */
    fun setTerminalTitle(title: String): DefaultTerminalFactory {
        this.title = title
        return this
    }

    /**
     * Sets the color configuration to use on created [SwingTerminalFrame] created by this factory
     */
    fun setTerminalColorConfiguration(colorConfiguration: ColorConfiguration): DefaultTerminalFactory {
        this.colorConfiguration = colorConfiguration
        return this
    }

    /**
     * Sets the device configuration to use on created [SwingTerminalFrame] created by this factory
     */
    fun setTerminalDeviceConfiguration(deviceConfiguration: DeviceConfiguration): DefaultTerminalFactory {
        this.deviceConfiguration = deviceConfiguration
        return this
    }

    fun setFontRenderer(fontRenderer: MonospaceFontRenderer<Graphics>) {
        this.fontRenderer = fontRenderer
    }

    // TODO: font renderer

    /**
     * Create a [Terminal] and immediately wrap it up in a [TerminalScreen]
     */
    fun createScreen(): TerminalScreen {
        return TerminalScreen(createTerminal())
    }

    fun createScreenFor(terminal: Terminal): TerminalScreen {
        return TerminalScreen(terminal)
    }

}