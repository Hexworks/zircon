package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED

object FocusHandlingExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val textArea = Components.textArea()
                .withText("Test focusing on me\nby clicking the\nbuttons.")
                .withSize(Size.create(20, 5))
                .withPosition(Position.create(2, 2))
                .build()

        val giveFocusBtn = Components.button()
                .withText("Give focus")
                .withPosition(Position.create(24, 2))
                .build()

        val takeFocusBtn = Components.button()
                .withText("Take focus")
                .withPosition(Position.create(24, 4))
                .build()

        screen.addComponent(textArea)
        screen.addComponent(giveFocusBtn)
        screen.addComponent(takeFocusBtn)

        giveFocusBtn.processComponentEvents(ACTIVATED) {
            textArea.requestFocus()
        }

        takeFocusBtn.processComponentEvents(ACTIVATED) {
            textArea.clearFocus()
        }

        screen.display()
        screen.theme = ColorThemes.ancestry()
    }

}
