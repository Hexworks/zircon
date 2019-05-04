package org.hexworks.zircon.examples.events

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.extensions.onMouseEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed

object InputHandlingExampleKotlin {

    private val SCREEN_SIZE = Sizes.create(80, 40)
    private val TILESET = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SCREEN_SIZE)
                .build())

        // called for all keyboard events
        tileGrid.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
            println("Keyboard event is: $event")
            Pass
        }


        tileGrid.onMouseEvent(MouseEventType.MOUSE_PRESSED) { event, _ ->
            println(event)
            Processed
        }
    }
}
