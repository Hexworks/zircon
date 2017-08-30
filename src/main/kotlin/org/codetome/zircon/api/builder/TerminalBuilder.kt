package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.resource.PhysicalFontResource
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.screen.TerminalScreen
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.terminal.swing.SwingTerminalFrame
import java.awt.Toolkit
import java.awt.image.BufferedImage


/**
 * Builds [Terminal]s.
 * Defaults are:
 * - default `initialSize` is 80x24
 * - default `title` is "Zircon Terminal"
 * - default `font` is `UBUNTU_MONO` (because it is cp437 compliant)
 * @see DeviceConfigurationBuilder for the defaults for `deviceConfiguration`
 */
class TerminalBuilder {

    private var initialSize: Size = Size.DEFAULT_TERMINAL_SIZE
    private var title: String = "Zircon Terminal"
    private var deviceConfiguration = DeviceConfigurationBuilder.getDefault()
    // TODO: refactor this to abstract shape when libgdx implementation comes
    private var font: Font<BufferedImage> = PhysicalFontResource.UBUNTU_MONO.toFont()

    /**
     * Builds a [Terminal] based on the properties of this [TerminalBuilder].
     */
    fun buildTerminal(): Terminal = buildSwingTerminal()

    /**
     * Builds a terminal which is backed by a Swing canvas. Currently this is the only
     * option.
     */
    fun buildSwingTerminal(): SwingTerminalFrame {
        checkScreenSize()
        return SwingTerminalFrame(
                title = title,
                size = initialSize,
                deviceConfiguration = deviceConfiguration,
                font = font).apply {
            isVisible = true
        }
    }

    /**
     * Sets the initial terminal [Size].
     * Default is 80x24.
     */
    fun initialTerminalSize(initialSize: Size) = also {
        this.initialSize = initialSize
    }


    /**
     * Sets the title to use on created [Terminal]s created by this shape.
     * Default is "Zircon Terminal"
     */
    fun title(title: String) = also {
        this.title = title
    }

    /**
     * Sets the device configuration to use on created [SwingTerminalFrame] created by this shape.
     */
    fun deviceConfiguration(deviceConfiguration: DeviceConfiguration) = also {
        this.deviceConfiguration = deviceConfiguration
    }

    /**
     * Sets a [Font] for this api.
     * @see [org.codetome.zircon.api.resource.CP437TilesetResource] and
     * @see PhysicalFontResource
     */
    fun font(font: Font<BufferedImage>) = also {
        this.font = font
    }

    /**
     * Creates a [Terminal] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen {
        return TerminalScreen(buildTerminal())
    }

    /**
     * Creates a [org.codetome.zircon.api.screen.Screen] for the given [Terminal].
     */
    fun createScreenFor(terminal: Terminal): Screen {
        return TerminalScreen(terminal)
    }

    private fun checkScreenSize() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        require(screenSize.width > font.getWidth() * initialSize.columns) {
            "The requested column count '${initialSize.columns}' for font width '${font.getWidth()}' won't fit on the screen (width: ${screenSize.width}"
        }
        require(screenSize.height > font.getHeight() * initialSize.rows) {
            "The requested row count '${initialSize.rows}' for font height '${font.getHeight()}' won't fit on the screen (height: ${screenSize.height}"
        }
    }

    companion object {

        /**
         * Creates a [Terminal] with default settings for all options.
         */
        @JvmStatic
        fun buildDefault() = newBuilder().buildTerminal()

        /**
         * Creates a new [Terminal] api.
         */
        @JvmStatic
        fun newBuilder() = TerminalBuilder()
    }
}