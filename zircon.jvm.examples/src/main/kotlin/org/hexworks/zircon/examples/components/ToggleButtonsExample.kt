package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.screen.Screen

object ToggleButtonsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withSize(Size.create(30, 28))
                .withAlignment(positionalAlignment(Position.create(29, 1)))
                .withDecorations(box(title = "Toolbar buttons on panel"), shadow())
                .build()
        screen.addComponent(panel)

        val unselectedToggleButton = Components.toggleButton()
                .withText("Toggle Me")
                .withAlignment(positionalAlignment(Position.create(1, 3)))
        val selectedToggleButton = Components.toggleButton()
                .withText("Toggle Me")
                .withIsSelected(true)
                .withAlignment(positionalAlignment(Position.create(1, 5)))

        // we can add them to both because these are just builders, so
        // 4 components will be built below
        screen.addComponent(unselectedToggleButton)
        screen.addComponent(selectedToggleButton)
        panel.addComponent(unselectedToggleButton)
        panel.addComponent(selectedToggleButton)


        screen.display()
        screen.theme = theme
    }

}
