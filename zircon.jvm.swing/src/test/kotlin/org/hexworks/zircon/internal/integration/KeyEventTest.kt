@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass

object KeyEventTest {

    private val tileset = CP437TilesetResources.rexPaint20x20()


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        tileGrid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, phase ->
            println("Event: $event, phase: $phase")
            Pass
        }

    }

}
