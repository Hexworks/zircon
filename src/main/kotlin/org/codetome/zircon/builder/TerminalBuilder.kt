package org.codetome.zircon.builder

import org.codetome.zircon.font.resource.DFTilesetResource
import org.codetome.zircon.font.Font
import org.codetome.zircon.screen.Screen
import org.codetome.zircon.screen.TerminalScreen
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.swing.SwingTerminalCanvas
import org.codetome.zircon.terminal.swing.SwingTerminalFrame
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import java.awt.image.BufferedImage


class TerminalBuilder {

    private var initialSize: Size = Size.DEFAULT
    private var title: String = "Zircon Terminal"
    private var autoOpenTerminalFrame = true
    private var deviceConfiguration = DeviceConfigurationBuilder.getDefault()
    // TODO: refactor this to abstract factory when libgdx implementation comes
    private var font: Font<BufferedImage> = DFTilesetResource.WANDERLUST_16X16.asJava2DFont()

    fun buildTerminal(): VirtualTerminal = buildTerminalEmulator()

    fun buildTerminalEmulator() = buildSwingTerminal()

    fun buildSwingTerminal() = SwingTerminalFrame(
            title = title,
            size = initialSize,
            deviceConfiguration = deviceConfiguration,
            font = font).apply {

        if (autoOpenTerminalFrame) {
            isVisible = true
        }
    }

    /**
     * Sets a hint to the [TerminalFactory] of what size to use when creating the terminal.
     * Most terminals are not created on request but for example the [SwingTerminalCanvas]
     * and [SwingTerminalFrame] are and this value will be passed down on
     * creation.
     */
    fun initialTerminalSize(initialSize: Size) = also {
        this.initialSize = initialSize
    }

    /**
     * Controls whether a [SwingTerminalFrame] shall be automatically shown (.setVisible(true))
     * immediately after creation.
     * If `false`, you will manually need to call `.setVisible(true)` on the JFrame to actually
     * see the terminal window. Default for this value is `true`.
     */
    fun autoOpenTerminalFrame(autoOpenTerminalFrame: Boolean) = also {
        this.autoOpenTerminalFrame = autoOpenTerminalFrame
    }

    /**
     * Sets the title to use on created [SwingTerminalFrame]s created by this factory
     */
    fun title(title: String) = also {
        this.title = title
    }

    /**
     * Sets the device configuration to use on created [SwingTerminalFrame] created by this factory.
     */
    fun deviceConfiguration(deviceConfiguration: DeviceConfiguration) = also {
        this.deviceConfiguration = deviceConfiguration
    }

    /**
     * Sets a [Font] for this builder.
     */
    fun font(font: Font<BufferedImage>) = also {
        this.font = font
    }

    /**
     * Creates a (default) [Terminal] and immediately wraps it up in a [TerminalScreen].
     */
    fun buildScreen(): Screen {
        return TerminalScreen(buildTerminal())
    }

    /**
     * Creates a [org.codetome.zircon.screen.Screen] for the given [Terminal].
     */
    fun createScreenFor(terminal: VirtualTerminal): TerminalScreen {
        return TerminalScreen(terminal)
    }

    companion object {

        /**
         * Creates a [Terminal] with default settings for all options.
         */
        @JvmStatic
        fun buildDefault() = newBuilder().buildTerminal()

        /**
         * Creates a new [Terminal] builder.
         */
        @JvmStatic
        fun newBuilder() = TerminalBuilder()
    }
}