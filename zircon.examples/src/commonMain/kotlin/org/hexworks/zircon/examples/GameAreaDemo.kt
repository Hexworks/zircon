package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import kotlin.random.Random

/**
 * A field of nethack sprites: graphical tiles drawn onto a layer over the content area.
 */
public val gameAreaDemo: Demo = Demo("Game area", CP437TilesetResources.wanderlust16x16(), textOnly = false) { context ->
    val nethack = GraphicalTilesetResources.nethack16x16()
    val names = listOf(
        "Giant ant", "Killer bee", "Soldier ant", "Fire ant", "Giant beetle", "Queen bee",
        "Acid blob", "Quivering blob", "Gelatinous cube", "Chickatrice", "Cockatrice", "Pyrolisk",
        "Jackal", "Fox", "Coyote", "Werejackal", "Little dog", "Dingo", "Dog", "Large dog", "Wolf",
    )
    val field = layer {
        size = context.contentSize
        offset = context.contentPosition
        tileset = nethack
    }
    val random = Random(7)
    for (y in 0 until field.size.height) for (x in 0 until field.size.width) {
        if (random.nextInt(4) == 0) {
            field.draw(graphicalTile { name = names[random.nextInt(names.size)]; tileset = nethack }, Position(x, y))
        }
    }
    context.screen.addLayer(field)
    DemoView(context.emptyContent())
}
