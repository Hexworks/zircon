package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.extensions.isEnabled
import org.hexworks.zircon.api.uievent.MouseEventType

object TextAreaDisableExample {

    private val theme = ColorThemes.ghostOfAChance()
    private val tileset = CP437TilesetResources.acorn8X16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val textArea = Components.textArea()
                .withText("Enabled")
                .withPosition(2, 2)
                .withSize(10, 3)
                .build()

        val toggleButton = Components.button()
                .withText("Toggle TextArea")
                .withPosition(14, 2)
                .build()

        toggleButton.processMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
            if (textArea.isEnabled) {
                textArea.isDisabled = true
                textArea.text = "Disabled"
            } else {
                textArea.isEnabled = true
                textArea.text = "Enabled"
            }
        }

        screen.addComponent(textArea)
        screen.addComponent(toggleButton)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
