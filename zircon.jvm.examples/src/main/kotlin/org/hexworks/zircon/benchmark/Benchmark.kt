package org.hexworks.zircon.benchmark

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import java.awt.Toolkit
import java.util.*

class Benchmark {

    val random = Random()

    fun execute(grid: TileGrid) = runBlocking {
        val gridWidth = BENCHMARK_SIZE.width
        val gridHeight = BENCHMARK_SIZE.height
        val layerWidth = BENCHMARK_SIZE.width / 2
        val layerHeight = BENCHMARK_SIZE.height / 2
        val layerCount = 20
        val layerSize = Size.create(layerWidth, layerHeight)
        val filler = Tile.defaultTile().withCharacter('x')

        val layers = (0..layerCount).map {
            val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .withSize(layerSize)
                .withTileset(BENCHMARK_TILESET)
                .build()
            layerSize.fetchPositions().forEach {
                imageLayer.draw(filler, it)
            }
            val layer = LayerBuilder.newBuilder()
                .withOffset(
                    Position.create(
                        x = random.nextInt(gridWidth - layerWidth),
                        y = random.nextInt(gridHeight - layerHeight)
                    )
                )
                .withTileGraphics(imageLayer)
                .build()

            grid.addLayer(layer)
            layer
        }

        val tiles = listOf(
            Tile.newBuilder().withCharacter('a').withStyleSet(
                StyleSet.create(
                    foregroundColor = ANSITileColor.YELLOW,
                    backgroundColor = ANSITileColor.BLUE
                )
            )
                .buildCharacterTile(),
            Tile.newBuilder().withCharacter('b').withStyleSet(
                StyleSet.create(
                    foregroundColor = ANSITileColor.GREEN,
                    backgroundColor = ANSITileColor.RED
                )
            )
                .buildCharacterTile()
        )

        grid.fill(tiles[0])

        var currIdx = 0

        val percentage = .15
        val fpsTarget = 60
        val interval = 1000.div(fpsTarget)
        var nextRender = System.currentTimeMillis() + interval
        while (true) {
            val now = System.currentTimeMillis()
            if (now < nextRender) {
                drawRandomTiles(grid, tiles[currIdx], percentage)
                layers.forEach {
                    it.asInternalLayer().moveTo(
                        Position.create(
                            x = random.nextInt(gridWidth - layerWidth),
                            y = random.nextInt(gridHeight - layerHeight)
                        )
                    )
                }
                currIdx = if (currIdx == 0) 1 else 0
                nextRender = System.currentTimeMillis() + interval
            } else {
                delay(nextRender - now)
            }
        }
    }

    private fun drawRandomTiles(
        tileGrid: TileGrid,
        tile: Tile,
        percentage: Double
    ) {
        repeat((tileGrid.size.width * tileGrid.size.height).times(percentage).toInt()) {
            tileGrid.draw(
                tile, Position.create(
                    x = random.nextInt(tileGrid.size.width),
                    y = random.nextInt(tileGrid.size.height)
                )
            )
        }
    }

    companion object {
        val BENCHMARK_DIMENSIONS = Toolkit.getDefaultToolkit().screenSize
        val BENCHMARK_TILESET = CP437TilesetResources.zaratustra16x16()
        val BENCHMARK_SIZE = Size.create(
            BENCHMARK_DIMENSIONS.width / BENCHMARK_TILESET.width,
            BENCHMARK_DIMENSIONS.height / BENCHMARK_TILESET.width
        )
    }
}
