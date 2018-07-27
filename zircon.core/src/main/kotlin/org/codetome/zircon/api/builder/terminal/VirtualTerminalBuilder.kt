package org.codetome.zircon.api.builder.terminal

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.DeviceConfiguration
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.tileset.impl.FontSettings
import org.codetome.zircon.internal.screen.TerminalScreen
import org.codetome.zircon.internal.terminal.InternalTerminal
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal

open class VirtualTerminalBuilder(
        protected var fullScreen: Boolean = false,
        protected var initialSize: Size = Size.defaultTerminalSize(),
        protected var title: String = "Zircon Terminal",
        protected var deviceConfiguration: DeviceConfiguration = DeviceConfiguration.defaultConfiguration(),
        protected var tileset: Tileset = FontSettings.NO_FONT
) : TerminalBuilder {

    override fun build(): Terminal {
        return VirtualTerminal(
                initialSize = initialSize,
                initialTileset = tileset)
    }

    override fun createCopy(): TerminalBuilder = VirtualTerminalBuilder(
            fullScreen = fullScreen,
            initialSize = initialSize,
            title = title,
            deviceConfiguration = deviceConfiguration,
            tileset = tileset)

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
     * Sets a [Tileset] for this api.
     * @see [org.codetome.zircon.api.resource.CP437TilesetResource] and
     * @see PhysicalFontResource
     */
    override fun font(tileset: Tileset) = also {
        this.tileset = tileset
    }

    /**
     * Creates a [Terminal] using this builder's settings and immediately wraps it up in a [Screen].
     */
    override fun buildScreen() = TerminalScreen(
            terminal = build() as InternalTerminal)

    companion object {

        /**
         * Creates a new [VirtualTerminalBuilder].
         */
        fun newBuilder() = VirtualTerminalBuilder()
    }
}
