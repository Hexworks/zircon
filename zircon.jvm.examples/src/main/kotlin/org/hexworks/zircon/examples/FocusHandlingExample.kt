package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED

object FocusHandlingExample {

    // TODO: focus handling is buggy, it won't take away highlight from previously focused
    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val textArea = Components.textArea()
                .withText("Test focusing on me\nby clicking the\nbuttons.")
                .withSize(Sizes.create(20, 5))
                .withPosition(Positions.create(2, 2))
                .build()

        val giveFocusBtn = Components.button()
                .withText("Give focus")
                .withPosition(Positions.create(24, 2))
                .build()

        val takeFocusBtn = Components.button()
                .withText("Take focus")
                .withPosition(Positions.create(24, 4))
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
        screen.applyColorTheme(ColorThemes.ancestry())
    }

}
