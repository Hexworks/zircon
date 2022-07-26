package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import java.util.*

object GraphicalTilesetExample {
    private const val TERMINAL_WIDTH = 50
    private const val TERMINAL_HEIGHT = 24
    private val SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT)
    private val TILESET = nethack16x16()
    private val NAMES = arrayOf(
        "Giant ant",
        "Killer bee",
        "Fire ant",
        "Werewolf",
        "Dingo",
        "Hell hound pup",
        "Tiger",
        "Gremlin"
    )
    private val RANDOM = Random()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = startTileGrid(
            AppConfig.newBuilder().withSize(SIZE).build()
        )
        for (row in 0 until TERMINAL_HEIGHT) {
            for (col in 0 until TERMINAL_WIDTH) {
                val name = NAMES[RANDOM.nextInt(NAMES.size)]
                tileGrid.draw(
                    Tile.newBuilder()
                        .withName(name)
                        .withTileset(TILESET)
                        .buildGraphicalTile(),
                    Position.create(col, row)
                )
            }
        }
    }
}