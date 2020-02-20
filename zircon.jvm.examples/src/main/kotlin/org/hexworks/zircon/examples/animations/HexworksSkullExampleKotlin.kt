package org.hexworks.zircon.examples.animations

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.examples.base.displayScreen
import org.hexworks.zircon.examples.base.hexworksSkull

object HexworksSkullExampleKotlin {

    @JvmStatic
    fun main(args: Array<String>) {

        displayScreen(ColorThemes.defaultTheme()).apply {
            start(hexworksSkull(
                    position = Position.create(width / 2 - 6, height / 2 - 12),
                    tileset = tileset))
        }

    }

}
