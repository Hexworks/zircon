package org.hexworks.zircon.examples.game

import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/** A field of nethack sprites: graphical tiles drawn onto a layer over the content area. */
public val spritesExample: Example = Example("Sprites", textOnly = false) { context ->
    val nethack = GraphicalTilesetResources.nethack16x16()
    val names = listOf(
        "Giant ant", "Killer bee", "Soldier ant", "Fire ant", "Giant beetle", "Queen bee",
        "Acid blob", "Quivering blob", "Gelatinous cube", "Chickatrice", "Cockatrice", "Pyrolisk",
        "Jackal", "Fox", "Coyote", "Werejackal", "Little dog", "Dingo", "Dog", "Large dog", "Wolf",
    )
    val field = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
        tileset = nethack
    }
    val random = Random(7)
    for (y in 0 until field.size.height) for (x in 0 until field.size.width) {
        if (random.nextInt(4) == 0) {
            field.draw(graphicalTile { name = names[random.nextInt(names.size)]; tileset = nethack }, Position(x, y))
        }
    }
    ExampleView(context.emptyContent(), layers = listOf(field))
}

public val gameExamples: List<Example> = listOf(spritesExample, cavesExample, scrollingExample, playerMoveExample, obliqueWorldExample)
