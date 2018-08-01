package org.codetome.zircon.api.builder.grid

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.grid.DeviceConfiguration
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.tileset.impl.FontSettings
import org.codetome.zircon.internal.screen.TileGridScreen
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.virtual.VirtualTileGrid

open class VirtualTerminalBuilder(
        protected var fullScreen: Boolean = false,
        protected var initialSize: Size = Size.defaultTerminalSize(),
        protected var title: String = "Zircon TileGrid",
        protected var deviceConfiguration: DeviceConfiguration = DeviceConfiguration.defaultConfiguration(),
        protected var tileset: Tileset = FontSettings.NO_FONT
) : TerminalBuilder {

    override fun build(): TileGrid {
        return VirtualTileGrid(
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
     * Sets the initial grid [Size].
     * Default is 80x24.
     */
    override fun initialTerminalSize(initialSize: Size) = also {
        this.initialSize = initialSize
    }

    /**
     * Sets the title to use on created [TileGrid]s created by this shape.
     * Default is "Zircon TileGrid"
     */
    override fun title(title: String) = also {
        this.title = title
    }

    override fun fullScreen() = also {
        fullScreen = true
    }

    /**
     * Sets the device configuration to use on the [TileGrid] being created.
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
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    override fun buildScreen() = TileGridScreen(
            terminal = build() as InternalTileGrid)

    companion object {

        /**
         * Creates a new [VirtualTerminalBuilder].
         */
        fun newBuilder() = VirtualTerminalBuilder()
    }
}
