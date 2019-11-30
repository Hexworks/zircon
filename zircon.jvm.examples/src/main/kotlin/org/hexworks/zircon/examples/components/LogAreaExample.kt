package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.screen.Screen

object LogAreaExample {

    private val tileset = CP437TilesetResources.aduDhabi16x16()
    private val theme = ColorThemes.amigaOs()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .enableBetaFeatures()
                .withSize(Size.create(70, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val logArea = Components.logArea()
                .withDecorations(box(title = "Log"))
                .withSize(70, 30)
                .build()

        logArea.addParagraph("This is a simple log row")

        logArea.addParagraph("This is a log row with a typing effect", withTypingEffectSpeedInMs = 200)
        logArea.addNewRows(2)

        logArea.addInlineText("This is a log row with a ")
        val btn = Components.button()
                .withText("Button")
                .build()
        logArea.addInlineComponent(btn)
        logArea.commitInlineElements()

        logArea.addNewRows(2)
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long")


        logArea.addNewRows(1)
        val paragraphStyleSet = ComponentStyleSets.newBuilder()
                .withDefaultStyle(StyleSet.create(ANSITileColor.YELLOW, TileColor.defaultBackgroundColor()))
                .build()
        logArea.addParagraph(ParagraphBuilder.newBuilder()
                .withText("This is a long log row, which gets wrapped, since it is long with a different style")
                .withComponentStyleSet(paragraphStyleSet))

        screen.addComponent(logArea)
        screen.theme = theme
        screen.display()


    }

}
