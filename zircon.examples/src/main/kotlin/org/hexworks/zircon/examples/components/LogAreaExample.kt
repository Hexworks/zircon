package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet

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

        logArea.addParagraph("This is a log row with a typing effect", withTypingEffectSpeedInMs = 200)
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
        screen.applyColorTheme(theme)

        logArea.addNewRows(1)
        val paragraphStyleSet = ComponentStyleSets.newBuilder()
                .withDefaultStyle(StyleSet.create(ANSITileColor.YELLOW, TileColor.defaultBackgroundColor()))
                .build()
        logArea.addParagraph(ParagraphBuilder.newBuilder()
                .withText("This is a long log row, which gets wrapped, since it is long with a different style")
                .withComponentStyleSet(paragraphStyleSet)
        )

        screen.display()


    }

}
