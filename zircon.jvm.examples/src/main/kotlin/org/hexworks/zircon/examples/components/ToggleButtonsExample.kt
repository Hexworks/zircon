package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.extensions.side

object ToggleButtonsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withSize(Sizes.create(30, 28))
                .withAlignment(positionalAlignment(Positions.create(29, 1)))
                .withDecorations(box(title = "Toolbar buttons on panel"), shadow())
                .build()
        screen.addComponent(panel)

        val unselectedToggleButton = Components.toggleButton()
                .withText("Toggle me")
                .withDecorations(side())
                .withAlignment(positionalAlignment(Positions.create(1, 3)))
        val selectedToggleButton = Components.toggleButton()
                .withText("Boxed Toggle Button")
                .withIsSelected(true)
                .withDecorations(box())
                .withAlignment(positionalAlignment(Positions.create(1, 5)))


        panel.addComponent(unselectedToggleButton)
        panel.addComponent(selectedToggleButton)


        screen.display()
        screen.applyColorTheme(theme)
    }

}
