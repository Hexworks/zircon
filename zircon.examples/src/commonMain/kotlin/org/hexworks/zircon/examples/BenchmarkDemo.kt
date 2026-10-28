package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import kotlin.random.Random

/**
 * Rewrites every tile of a large grid on every frame, the worst case for the renderer.
 */
public fun benchmarkDemo(): TileGrid {
    val cols = 120
    val rows = 60
    val grid = createTileGrid(
        appConfig {
            size = Size(cols, rows)
            defaultTileset = CP437TilesetResources.rexPaint8x8()
        }
    )
    val random = Random(42)
    val chars = (33..126).map { it.toChar() }
    fun fill() {
        for (y in 0 until rows) for (x in 0 until cols) {
            grid.draw(
                characterTile {
                    character = chars[random.nextInt(chars.size)]
                    withStyleSet {
                        foregroundColor = Color.create(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                        backgroundColor = Color.create(random.nextInt(64), random.nextInt(64), random.nextInt(64))
                    }
                },
                Position(x, y)
            )
        }
    }
    fill()
    BenchmarkTicker.register(grid) { fill() }
    return grid
}

/**
 * Per-grid callbacks the examples app runs every frame (the composable calls [tick]).
 */
public object BenchmarkTicker {
    private val callbacks = mutableMapOf<TileGrid, () -> Unit>()

    public fun register(grid: TileGrid, callback: () -> Unit) {
        callbacks[grid] = callback
    }

    public fun tick(grid: TileGrid) {
        callbacks[grid]?.invoke()
    }
}
