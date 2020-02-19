package org.hexworks.zircon.examples.animations

import org.hexworks.zircon.api.data.Position.Companion.create
import org.hexworks.zircon.examples.base.displayDefaultScreen
import org.hexworks.zircon.examples.base.hexworksSkull

object HexworksSkullExampleKotlin {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayDefaultScreen()

        screen.display()

        screen.start(hexworksSkull(
                create(screen.width / 2 - 6, screen.height / 2 - 12),
                screen.tileset))

    }

}
