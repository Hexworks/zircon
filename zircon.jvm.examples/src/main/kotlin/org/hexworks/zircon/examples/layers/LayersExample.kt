package org.hexworks.zircon.examples.layers

import org.hexworks.zircon.api.CP437TilesetResources.rogueYun16x16
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.color.TileColor.Companion.create
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid

fun main(args: Array<String>) {
    LayersExample().run()
}

class LayersExample {

    private val TERMINAL_WIDTH = 45
    private val TERMINAL_HEIGHT = 5
    private val SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT)

    fun run() {
        val tileGrid: TileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(rogueYun16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build()
        )
        val firstRow = "This is white title on black"
        for (x in 0 until firstRow.length) {
            tileGrid.draw(
                buildWhiteOnBlack(firstRow[x]),
                Position.create(x + 1, 1)
            )
        }
        val secondRow = "Like the row above but with blue overlay."
        for (x in 0 until secondRow.length) {
            tileGrid.draw(
                buildWhiteOnBlack(secondRow[x]),
                Position.create(x + 1, 2)
            )
        }
        addOverlayAt(
            tileGrid,
            Position.create(1, 2),
            Size.create(secondRow.length, 1),
            create(50, 50, 200, 125)
        )
    }

    private fun addOverlayAt(tileGrid: TileGrid, offset: Position, size: Size, color: TileColor) {
        tileGrid.addLayer(
            LayerBuilder.newBuilder()
                .withOffset(offset)
                .withSize(size)
                .withFiller(
                    Tile.newBuilder()
                        .withBackgroundColor(color)
                        .withCharacter(' ')
                        .build()
                )
                .build()
        )
    }

    private fun buildWhiteOnBlack(c: Char): Tile {
        return Tile.newBuilder()
            .withCharacter(c)
            .withBackgroundColor(create(0, 0, 0, 255))
            .withForegroundColor(create(255, 255, 255, 255))
            .build()
    }
}