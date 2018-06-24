package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.resource.PhysicalFontResource
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.DeviceConfiguration
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.screen.TerminalScreen
import org.codetome.zircon.internal.terminal.InternalTerminal
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal

open class VirtualTerminalBuilder(
        protected var fullScreen: Boolean = false,
        protected var initialSize: Size = Size.defaultTerminalSize(),
        protected var title: String = "Zircon Terminal",
        protected var deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.DEFAULT,
        protected var font: Font = FontSettings.NO_FONT
) : TerminalBuilder {

    override fun build(): Terminal {
        font = DEFAULT_FONT.toFont()
        return VirtualTerminal(
                initialSize = initialSize,
                initialFont = font)
    }

    override fun createCopy(): TerminalBuilder = VirtualTerminalBuilder(
            fullScreen = fullScreen,
            initialSize = initialSize,
            title = title,
            deviceConfiguration = deviceConfiguration,
            font = font)

    /**
     * Sets the initial terminal [Size].
     * Default is 80x24.
     */
    override fun initialTerminalSize(initialSize: Size) = also {
        this.initialSize = initialSize
    }

    /**
     * Sets the title to use on created [Terminal]s created by this shape.
     * Default is "Zircon Terminal"
     */
    override fun title(title: String) = also {
        this.title = title
    }

    override fun fullScreen() = also {
        fullScreen = true
    }

    /**
     * Sets the device configuration to use on the [Terminal] being created.
     */
    override fun deviceConfiguration(deviceConfiguration: DeviceConfiguration) = also {
        this.deviceConfiguration = deviceConfiguration
    }

    /**
     * Sets a [Font] for this api.
     * @see [org.codetome.zircon.api.resource.CP437TilesetResource] and
     * @see PhysicalFontResource
     */
    override fun font(font: Font) = also {
        this.font = font
    }

    /**
     * Creates a [Terminal] using this builder's settings and immediately wraps it up in a [Screen].
     */
    override fun buildScreen() = TerminalScreen(
            terminal = build() as InternalTerminal)

    companion object {

        @JvmStatic
        val DEFAULT_FONT = PhysicalFontResource.UBUNTU_MONO

        /**
         * Creates a new [VirtualTerminalBuilder].
         */
        @JvmStatic
        fun newBuilder() = VirtualTerminalBuilder()
    }
}
