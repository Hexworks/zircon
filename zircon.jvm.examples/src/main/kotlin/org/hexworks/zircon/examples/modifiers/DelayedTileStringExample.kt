package org.hexworks.zircon.examples.modifiers


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box

object DelayedTileStringExample {

    private val SIZE = Size.create(50, 30)
    private val TILESET = CP437TilesetResources.taffer20x20()
    private val THEME = ColorThemes.cyberpunk()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .build()
        )

        val screen = ScreenBuilder.createScreenFor(tileGrid)

        val panel = Components.panel()
            .withDecorations(box())
            .withPreferredSize(Size.create(48, 20))
            .build()

        screen.addComponent(panel)

        val myText = "This is a very long string I'd like to add with a typrewriter effect"

        panel.addComponent(
            Components.textBox(panel.contentSize.width)
                .addParagraph(
                    text = myText,
                    withTypingEffectSpeedInMs = 200
                )
        )

        screen.theme = THEME
        screen.display()

    }
}
