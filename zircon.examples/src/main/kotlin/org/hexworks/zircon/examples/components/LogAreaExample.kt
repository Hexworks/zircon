package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.resource.ColorThemeResource

object LogAreaExample {

    private val tileset = CP437TilesetResources.aduDhabi16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 20))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize((Sizes.create(52, 15)))
                .withTitle("Log")
                .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
                .withSize(Sizes.create(50, 10))
                .withPosition(Positions.create(0, 0))
                .wrapLogElements(true)
                .build()

        logArea.addTextElement("This is a simple log row")
        logArea.addNewRows()
        logArea.addTextElement("This is a further log row with a modifier", setOf(Modifiers.crossedOut()))
        logArea.addNewRows(2)

        logArea.addTextElement("This is a log row with a ")
        val btn = Components.button()
                .withDecorationRenderers()
                .text("Button")
                .build()
        logArea.addComponentElement(btn)

        logArea.addNewRows(2)
        logArea.addTextElement("This is a long log row, which gets wrapped, since it is long")

        panel.addComponent(logArea)

        screen.display()
        screen.applyColorTheme(ColorThemeResource.CYBERPUNK.getTheme())

    }

}
