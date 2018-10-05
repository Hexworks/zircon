package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.resource.ColorThemeResource

object LogAreaExample {

    private val tileset = CP437TilesetResources.aduDhabi16x16()
    private val theme = ColorThemes.amigaOs()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(70, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize((Sizes.create(60, 25)))
                .withPosition(Position.defaultPosition())
                .withTitle("Log")
                .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
                .withSize(Sizes.create(50, 10))
                .wrapLogElements(true)
                //.delayInMsForTypewriterEffect(500)
                .withPosition(Position.defaultPosition())
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
        screen.applyColorTheme(theme)

    }

}
