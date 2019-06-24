package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.onTextChanged
import org.hexworks.zircon.api.extensions.shadow

object TextHandlingExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

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
        screen.applyColorTheme(theme)

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
