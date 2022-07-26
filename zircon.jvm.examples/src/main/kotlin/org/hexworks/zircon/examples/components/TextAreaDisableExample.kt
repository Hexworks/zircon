package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.isEnabled
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType

object TextAreaDisableExample {

    private val theme = ColorThemes.ghostOfAChance()
    private val tileset = CP437TilesetResources.acorn8X16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build()
        )

        val screen = Screen.create(tileGrid)

        val textArea = Components.textArea()
            .withText("Enabled")
            .withPosition(2, 2)
            .withPreferredSize(10, 3)
            .build()

        val toggleButton = Components.button()
            .withText("Toggle TextArea")
            .withPosition(14, 2)
            .build()

        toggleButton.processComponentEvents(ComponentEventType.ACTIVATED) {
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
        screen.theme = theme
    }

}
