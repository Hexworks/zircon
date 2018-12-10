package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.kotlin.onMouseClicked

object FocusHandlingExample {


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

        giveFocusBtn.onMouseClicked {
            textArea.requestFocus()
        }

        takeFocusBtn.onMouseClicked {
            textArea.clearFocus()
        }

        screen.display()
        screen.applyColorTheme(ColorThemes.ancestry())
    }

}
