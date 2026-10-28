package org.hexworks.zircon.examples

import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import kotlin.random.Random

/**
 * A field of nethack sprites: graphical tiles drawn straight onto the grid.
 */
public fun gameAreaDemo(): TileGrid {
    val nethack = GraphicalTilesetResources.nethack16x16()
    val grid = createTileGrid(
        appConfig {
            size = Size(40, 20)
            defaultTileset = nethack
        }
    )
    val names = listOf(
        "Giant ant", "Killer bee", "Soldier ant", "Fire ant", "Giant beetle", "Queen bee",
        "Acid blob", "Quivering blob", "Gelatinous cube", "Chickatrice", "Cockatrice", "Pyrolisk",
        "Jackal", "Fox", "Coyote", "Werejackal", "Little dog", "Dingo", "Dog", "Large dog", "Wolf",
    )
    val random = Random(7)
    for (y in 0 until 20) for (x in 0 until 40) {
        if (random.nextInt(4) == 0) {
            grid.draw(graphicalTile { name = names[random.nextInt(names.size)]; tileset = nethack }, Position(x, y))
        }
    }
    return grid
}
