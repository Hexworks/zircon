package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.screen.ScreenBuilder

object DelayedTileStringExample {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = CP437TilesetResources.taffer20x20()
    private val THEME = ColorThemes.cyberpunk()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withDebugMode(true)
                .build())

        val screen = ScreenBuilder.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox()
                .withSize(Sizes.create(48, 20))
                .build()

        screen.addComponent(panel)

        val myText = "This is a very long string I'd like to add with a typrewriter effect"

        panel.addComponent(Components.textBox(panel.contentSize.width)
                .addParagraph(paragraph = myText,
                        withTypingEffectSpeedInMs = 200))

        screen.applyColorTheme(THEME)
        screen.display()

    }
}
