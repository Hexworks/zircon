package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.screen.Screen

object TextHandlingExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = TrueTypeFontResources.ibmBios(20)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val label = Components.label()
                .withText("Foobar")
                .withDecorations(shadow())
                .build()

        val otherLabel = Components.label()
                .withText("Barbaz")
                .withDecorations(shadow())
                .withPosition(0, 3)
                .build()

        screen.addComponent(label)
        screen.addComponent(otherLabel)

        screen.display()
        screen.theme = theme

        label.onTextChanged {
            println("Text changed")
        }

        label.text = "baz"

        label.textProperty.value = "xul"

        label.textProperty.bind(otherLabel.textProperty)

        // Text changed
        // Text changed
        // Text changed

    }

}
