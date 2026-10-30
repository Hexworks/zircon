package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * Rewrites every tile of the content area on every frame, the worst case for a renderer.
 * Pick a small tileset for a big grid.
 */
public val benchmarkExample: Example = Example("Benchmark") { context ->
    val field = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    }
    val random = Random(42)
    val chars = (33..126).map { it.toChar() }
    // A fixed palette of random tiles: the point is rewriting every cell every frame, not
    // building thousands of tiles with the builder DSL each time.
    val palette = List(PALETTE_SIZE) {
        characterTile {
            character = chars[random.nextInt(chars.size)]
            withStyleSet {
                foregroundColor = Color.create(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                backgroundColor = Color.create(random.nextInt(64), random.nextInt(64), random.nextInt(64))
            }
        }
    }
    fun fill() {
        for (y in 0 until field.size.height) for (x in 0 until field.size.width) {
            field.draw(palette[random.nextInt(PALETTE_SIZE)], Position(x, y))
        }
    }
    fill()
    ExampleView(context.emptyContent(), layers = listOf(field), onFrame = ::fill)
}

public val otherExamples: List<Example> = listOf(
    bspExample,
    tileStringsExample,
    colorInterpolationExample,
    cursorExample,
    gameMockupExample,
    graphicalExample,
    multiTileExample,
    rexLoaderExample,
    stackedTileExample,
    statsPanelExample,
    tilesetExample,
    trueTypeFontExample,
    typingExample,
    benchmarkExample,
)

private const val PALETTE_SIZE = 1024
