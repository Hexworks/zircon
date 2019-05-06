package org.hexworks.zircon.examples.components

import org.hexworks.cobalt.databinding.api.expression.not
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.ComponentEventType.*
import org.hexworks.zircon.api.uievent.Processed

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
                .withPosition(20, 5)
                .withText("Toggle")
                .build().apply {
                    checkBox.enabledProperty.bind(selectedProperty)
                }

        screen.addComponent(checkBox)
        screen.addComponent(disableButton)


        screen.display()
        screen.applyColorTheme(theme)
    }

}
