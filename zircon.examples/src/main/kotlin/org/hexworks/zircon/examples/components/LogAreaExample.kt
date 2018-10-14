package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*

object LogAreaExample {

    private val tileset = CP437TilesetResources.aduDhabi16x16()
    private val theme = ColorThemes.amigaOs()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(70, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize((Sizes.create(60, 25)))
                .withTitle("Log")
                .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
                .withSize(Sizes.create(58, 23))
                .build()

        logArea.addParagraph("This is a simple log row")
        logArea.addParagraph("This is a further log row with a modifier", withTypingEffect = true)
        logArea.addNewRows(2)

        logArea.addInlineText("This is a log row with a ")
        val btn = Components.button()
                .withDecorationRenderers()
                .withText("Button")
                .build()
        logArea.addInlineComponent(btn)
        logArea.commitInlineElements()

        logArea.addNewRows(2)
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long")

        panel.addComponent(logArea)

        screen.display()
        screen.applyColorTheme(theme)


    }

}
