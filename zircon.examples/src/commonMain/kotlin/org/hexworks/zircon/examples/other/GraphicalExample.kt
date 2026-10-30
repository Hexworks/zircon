package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/** The content area filled with random monsters from the nethack sprite sheet. */
public val graphicalExample: Example = Example("Graphical", textOnly = false) { context ->
    val tileset = GraphicalTilesetResources.nethack16x16()
    val names = listOf("Giant ant", "Killer bee", "Fire ant", "Werewolf", "Dingo", "Hell hound pup", "Tiger", "Gremlin")
    val random = Random(3)
    val field = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
        this.tileset = tileset
    }
    for (y in 0 until field.size.height) for (x in 0 until field.size.width) {
        field.draw(graphicalTile { name = names[random.nextInt(names.size)]; this.tileset = tileset }, Position(x, y))
    }
    ExampleView(context.emptyContent(), layers = listOf(field))
}
