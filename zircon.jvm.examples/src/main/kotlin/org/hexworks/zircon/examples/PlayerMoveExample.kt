package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Layers
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.Processed

object PlayerMoveExample {

    val PLAYER_TILE = Tiles.newBuilder()
            .withBackgroundColor(ANSITileColor.BLACK)
            .withForegroundColor(ANSITileColor.WHITE)
            .withCharacter('@')
            .buildCharacterTile()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val player = Layers.newBuilder()
                .withSize(Sizes.one())
                .withOffset(Positions.create(SIZE.width / 2, SIZE.height / 2))
                .build()
                .fill(PLAYER_TILE)

        tileGrid.onKeyboardEvent(KEY_PRESSED) { event, _ ->
            when (event.code) {
                KeyCode.UP -> player.moveUpBy(1)
                KeyCode.DOWN -> player.moveDownBy(1)
                KeyCode.LEFT -> player.moveLeftBy(1)
                KeyCode.RIGHT -> player.moveRightBy(1)
            }
            Processed
        }

        tileGrid.pushLayer(player)
    }
}
