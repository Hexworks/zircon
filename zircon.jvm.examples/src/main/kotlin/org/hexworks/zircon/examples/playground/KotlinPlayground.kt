@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.StopPropagation


object KotlinPlayground {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val theme = ColorThemes.ghostOfAChance()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = tileGrid.toScreen()

        val logArea = Components.logArea()
                .withDecorations(box())
                .withAlignmentWithin(screen, CENTER)
                .withSize(20, 5)
                .withLogRowHistorySize(100)
                .build()

        screen.addComponent(logArea)

        logArea.addParagraph("Text", false, 500)

        logArea.addParagraph("Very long text", false, 50)

        screen.display()

        screen.theme = theme

        Thread.sleep(4000)

        screen.theme = ColorThemes.adriftInDreams()
    }
}
