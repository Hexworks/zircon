@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.onActivated
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.uievent.ComponentEventType


object KotlinPlayground {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val theme = ColorThemes.ghostOfAChance()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen0 = tileGrid.toScreen()
        val screen1 = tileGrid.toScreen()

        val btn0 = Components.button().withText("Button 0").build().apply {
            onActivated {
                screen1.display()
            }
        }
        val btn1 = Components.button().withText("Button 1").build().apply {
            onActivated {
                screen0.display()
            }
        }

        screen0.addComponent(btn0)
        screen1.addComponent(btn1)

        screen0.display()

        screen0.theme = theme
        screen1.theme = ColorThemes.adriftInDreams()
    }
}
