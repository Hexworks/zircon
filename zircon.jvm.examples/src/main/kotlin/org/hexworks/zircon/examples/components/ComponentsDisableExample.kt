package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.positionalAlignment

object ComponentsDisableExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val checkBox = Components.checkBox()
                .withSize(10, 1)
                .withPosition(5, 5)
                .withText("Check me")
                .build()
        val disableButton = Components.toggleButton()
                .withSize(17, 1)
                .withAlignment(positionalAlignment(20, 5))
                .withText("Toggle")
                .build().apply {
                    checkBox.disabledProperty.updateFrom(selectedProperty)
                }

        screen.addComponent(checkBox)
        screen.addComponent(disableButton)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
