package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import kotlin.random.Random

/**
 * Rewrites every tile of the content area on every frame, the worst case for the renderer.
 * Uses 8x8 tiles, so a full HD window holds 240x135 of them.
 */
public val benchmarkDemo: Demo = Demo("Benchmark", CP437TilesetResources.rexPaint8x8()) { context ->
    val field = layer {
        size = context.contentSize
        offset = context.contentPosition
    }
    val random = Random(42)
    val chars = (33..126).map { it.toChar() }
    // A fixed palette of random tiles: the point is rewriting every cell every frame, not
    // building 32k tiles with the builder DSL each time.
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
    context.screen.addLayer(field)
    DemoView(context.emptyContent(), onFrame = ::fill)
}

private const val PALETTE_SIZE = 1024
