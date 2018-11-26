package org.hexworks.zircon.internal

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

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
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withSize(Sizes.create(30, 28))
                .withPosition(Positions.create(29, 1))
                .withTitle("Toolbar buttons on panel")
                .build()
        screen.addComponent(panel)

        val unselectedToggleButton = Components.toggleButton()
                .withText("Toggle me")
                .wrapSides(true)
                .withPosition(Positions.create(1, 3))
        val selectedToggleButton = Components.toggleButton()
                .withText("Boxed Toggle Button")
                .withIsSelected(true)
                .wrapWithBox(true)
                .wrapSides(false)
                .withPosition(Positions.create(1, 5))


        panel.addComponent(unselectedToggleButton)
        panel.addComponent(selectedToggleButton)


        screen.display()
        screen.applyColorTheme(theme)
    }

}
