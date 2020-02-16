@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.extensions.toScreen
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

        val textBox = Components.textArea()
                .withSize(20, 10)
                .withAlignmentWithin(screen, CENTER)
                .build()

        screen.addComponent(textBox)

        screen.display()

        textBox.requestFocus()
        textBox.handleKeyboardEvents(KEY_PRESSED) { event, phase ->
            if (event.code == KeyCode.TAB) {
                println("=================== Stopped propagation")
                StopPropagation
            } else Pass
        }

        screen.theme = theme
    }
}
