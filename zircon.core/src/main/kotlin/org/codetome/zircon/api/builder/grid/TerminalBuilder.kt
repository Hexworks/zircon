package org.codetome.zircon.api.builder.grid

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.grid.DeviceConfiguration
import org.codetome.zircon.api.grid.TileGrid


/**
 * Builds [TileGrid]s.
 * Defaults are:
 * - default `initialSize` is 80x24
 * - default `title` is "Zircon TileGrid"
 * - default `tileset` is `UBUNTU_MONO` (because it is cp437 compliant)
 */
interface TerminalBuilder : Builder<TileGrid> {

    /**
     * Creates a new [TileGrid] from the given parameters
     */
    override fun build(): TileGrid

    override fun createCopy(): TerminalBuilder

    /**
     * Sets the initial grid [Size].
     * Default is 80x24.
     */
    fun initialTerminalSize(initialSize: Size): TerminalBuilder

    /**
     * Sets the title to use on created [TileGrid]s created by this shape.
     * Default is "Zircon TileGrid"
     */
    fun title(title: String): TerminalBuilder

    fun fullScreen(): TerminalBuilder

    /**
     * Sets the device configuration to use on the [TileGrid] being created.
     */
    fun deviceConfiguration(deviceConfiguration: DeviceConfiguration): TerminalBuilder

    /**
     * Sets a [Tileset] for this api.
     * @see [org.codetome.zircon.api.resource.CP437TilesetResource] and
     * @see PhysicalFontResource
     */
    fun font(tileset: Tileset): TerminalBuilder

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen
}
