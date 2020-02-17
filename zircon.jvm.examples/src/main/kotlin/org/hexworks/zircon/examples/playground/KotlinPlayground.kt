@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.toScreen


object KotlinPlayground {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val theme = ColorThemes.ghostOfAChance()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = tileGrid.toScreen()

        val btn = Components.button()
                .withText("Disabled")
                .withPosition(0, 1)
                .build()


        screen.addComponent(Components.button().withText("Enabled"))
        screen.addComponent(btn)

        btn.isDisabled = true

        screen.display()
        screen.theme = theme
    }
}
