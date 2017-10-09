package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.resource.PhysicalFontResource
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.screen.TerminalScreen
import org.codetome.zircon.internal.terminal.InternalTerminal
import org.codetome.zircon.internal.terminal.swing.SwingTerminalFrame
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
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
data class TerminalBuilder(
        private var fullScreen: Boolean = false,
        private var initialSize: Size = Size.DEFAULT_TERMINAL_SIZE,
        private var title: String = "Zircon Terminal",
        private var deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.DEFAULT,
        // TODO: refactor this to abstract shape when libgdx implementation comes
        private var font: Font<BufferedImage> = PhysicalFontResource.UBUNTU_MONO.toFont()
) : Builder<Terminal> {


    override fun build(): Terminal {
        return buildTerminal(false)
    }

    override fun createCopy() = copy()

    /**
     * Builds a [Terminal] based on the properties of this [TerminalBuilder].
     */
    fun buildTerminal(headless: Boolean = false): Terminal = buildInternalTerminal(headless)

    private fun buildInternalTerminal(headless: Boolean = false): InternalTerminal =
            if (headless) {
                VirtualTerminal(
                        initialSize = initialSize,
                        initialFont = font)
            } else {
                buildSwingTerminal()
            }

    /**
     * Builds a terminal which is backed by a Swing canvas. Currently this is the only
     * option.
     */
    private fun buildSwingTerminal(): SwingTerminalFrame {
        checkScreenSize()
        return SwingTerminalFrame(
                title = title,
                size = initialSize,
                deviceConfiguration = deviceConfiguration,
                fullScreen = fullScreen,
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

    fun fullScreen() = also {
        fullScreen = true
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
        val terminal = buildInternalTerminal()
        return TerminalScreen(
                initialFont = terminal.getCurrentFont(),
                terminal = terminal)
    }

    private fun checkScreenSize() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        require(screenSize.width >= font.getWidth() * initialSize.columns) {
            "The requested column count '${initialSize.columns}' for font width '${font.getWidth()}' won't fit on the screen (width: ${screenSize.width}"
        }
        require(screenSize.height >= font.getHeight() * initialSize.rows) {
            "The requested row count '${initialSize.rows}' for font height '${font.getHeight()}' won't fit on the screen (height: ${screenSize.height}"
        }
    }

    companion object {

        /**
         * Creates a new [Terminal] api.
         */
        @JvmStatic
        fun newBuilder() = TerminalBuilder()

        /**
         * Creates a [org.codetome.zircon.api.screen.Screen] for the given [Terminal].
         */
        @JvmStatic
        fun createScreenFor(terminal: Terminal): Screen {
            val font = terminal.getCurrentFont()
            return TerminalScreen(
                    initialFont = font,
                    terminal = terminal as InternalTerminal)
        }
    }
}