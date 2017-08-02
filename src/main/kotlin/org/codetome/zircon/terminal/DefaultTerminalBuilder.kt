package org.codetome.zircon.terminal

import org.codetome.zircon.builder.DeviceConfigurationBuilder
import org.codetome.zircon.font.FontRenderer
import org.codetome.zircon.screen.TerminalScreen
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.builder.FontRendererBuilder
import org.codetome.zircon.terminal.swing.SwingTerminalComponent
import org.codetome.zircon.terminal.swing.SwingTerminalFrame
import java.awt.Graphics


class DefaultTerminalBuilder
    : TerminalFactory {

    private var initialTerminalSize: TerminalSize = TerminalSize.DEFAULT
    private var title: String = "Zircon Terminal"
    private var autoOpenTerminalFrame = true
    private var deviceConfiguration = DeviceConfigurationBuilder.getDefault()
    private var fontRenderer: FontRenderer<Graphics> = FontRendererBuilder.getDefault()

    override fun buildTerminal(): Terminal = buildTerminalEmulator()

    fun buildTerminalEmulator() = buildSwingTerminal()

    fun buildSwingTerminal() = SwingTerminalFrame(
            title = title,
            terminalSize = initialTerminalSize,
            deviceConfiguration = deviceConfiguration,
            fontConfiguration = fontRenderer).apply {

        if (autoOpenTerminalFrame) {
            isVisible = true
        }
    }

    /**
     * Sets a hint to the [TerminalFactory] of what size to use when creating the terminal.
     * Most terminals are not created on request but for example the [SwingTerminalComponent]
     * and [SwingTerminalFrame] are and this value will be passed down on
     * creation.
     */
    fun initialTerminalSize(initialTerminalSize: TerminalSize) = also {
        this.initialTerminalSize = initialTerminalSize
    }

    /**
     * Controls whether a [SwingTerminalFrame] shall be automatically shown (.setVisible(true))
     * immediately after creation.
     * If `false`, you will manually need to call `.setVisible(true)` on the JFrame to actually
     * see the terminal window. Default for this value is `true`.
     */
    fun autoOpenTerminalWindow(autoOpenTerminalFrame: Boolean) = also {
        this.autoOpenTerminalFrame = autoOpenTerminalFrame
    }

    /**
     * Sets the title to use on created [SwingTerminalFrame]s created by this factory
     */
    fun setTerminalTitle(title: String) = also {
        this.title = title
    }

    /**
     * Sets the device configuration to use on created [SwingTerminalFrame] created by this factory.
     */
    fun terminalDeviceConfiguration(deviceConfiguration: DeviceConfiguration) = also {
        this.deviceConfiguration = deviceConfiguration
    }

    /**
     * Sets a [FontRenderer] for this builder
     */
    fun fontRenderer(fontRenderer: FontRenderer<Graphics>) = also {
        this.fontRenderer = fontRenderer
    }

    /**
     * Creates a (default) [Terminal] and immediately wraps it up in a [TerminalScreen].
     */
    fun buildScreen(): TerminalScreen {
        return TerminalScreen(buildTerminal())
    }

    /**
     * Creates a [org.codetome.zircon.screen.Screen] for the given [Terminal].
     */
    fun createScreenFor(terminal: Terminal): TerminalScreen {
        return TerminalScreen(terminal)
    }

    companion object {

        /**
         * Creates a [Terminal] with default settings for all options.
         */
        @JvmStatic
        fun buildDefault() = DefaultTerminalBuilder.newBuilder().buildTerminal()

        /**
         * Creates a new [Terminal] builder.
         */
        @JvmStatic
        fun newBuilder() = DefaultTerminalBuilder()
    }
}