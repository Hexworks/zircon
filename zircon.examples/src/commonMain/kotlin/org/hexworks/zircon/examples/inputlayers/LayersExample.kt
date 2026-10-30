package org.hexworks.zircon.examples.inputlayers

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Two rows of white-on-black text; a second layer over the lower row recolours it blue.
 * There is no alpha blending in 2.0, so the overlay repeats the characters with the new
 * colours instead of tinting them.
 */
public val layersExample: Example = Example("Layers") { context ->
    val origin = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    val base = layer {
        size = Size(60, 5)
        offset = origin
    }
    val firstRow = "This is white title on black"
    val secondRow = "Like the row above but with blue overlay."
    fun whiteOnBlack(c: Char) = characterTile {
        character = c
        withStyleSet {
            foregroundColor = Color.create(255, 255, 255)
            backgroundColor = Color.create(0, 0, 0)
        }
    }
    firstRow.forEachIndexed { x, c -> base.draw(whiteOnBlack(c), Position(x + 1, 1)) }
    secondRow.forEachIndexed { x, c -> base.draw(whiteOnBlack(c), Position(x + 1, 2)) }

    val overlay = layer {
        size = Size(secondRow.length, 1)
        offset = origin + Position(1, 2)
    }
    secondRow.forEachIndexed { x, c ->
        overlay.draw(characterTile {
            character = c
            withStyleSet {
                foregroundColor = Color.create(255, 255, 255)
                backgroundColor = Color.create(50, 50, 200)
            }
        }, Position(x, 0))
    }
    ExampleView(context.emptyContent(), layers = listOf(base, overlay))
}
