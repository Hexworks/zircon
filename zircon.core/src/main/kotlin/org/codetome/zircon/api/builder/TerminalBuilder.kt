package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.DeviceConfiguration
import org.codetome.zircon.api.terminal.Terminal


/**
 * Builds [Terminal]s.
 * Defaults are:
 * - default `initialSize` is 80x24
 * - default `title` is "Zircon Terminal"
 * - default `font` is `UBUNTU_MONO` (because it is cp437 compliant)
 * @see DeviceConfigurationBuilder for the defaults for `deviceConfiguration`
 */
interface TerminalBuilder : Builder<Terminal> {


    override fun build(): Terminal

    override fun createCopy(): TerminalBuilder

    /**
     * Sets the initial terminal [Size].
     * Default is 80x24.
     */
    fun initialTerminalSize(initialSize: Size): TerminalBuilder


    /**
     * Sets the title to use on created [Terminal]s created by this shape.
     * Default is "Zircon Terminal"
     */
    fun title(title: String): TerminalBuilder

    fun fullScreen(): TerminalBuilder

    /**
     * Sets the device configuration to use on the [Terminal] being created.
     */
    fun deviceConfiguration(deviceConfiguration: DeviceConfiguration): TerminalBuilder

    /**
     * Sets a [Font] for this api.
     * @see [org.codetome.zircon.api.resource.CP437TilesetResource] and
     * @see PhysicalFontResource
     */
    fun font(font: Font): TerminalBuilder

    /**
     * Creates a [Terminal] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen
}
