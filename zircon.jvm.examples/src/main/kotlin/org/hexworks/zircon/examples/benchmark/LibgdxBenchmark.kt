package org.hexworks.zircon.examples.benchmark

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.data.GridPosition
import java.util.*

fun main(args: Array<String>) {

    val size = Size.create(80, 40)

    val tileset = CP437TilesetResources.zaratustra16x16()

    val tileGrid = LibgdxApplications.startTileGrid(AppConfigBuilder.newBuilder()
            .withSize(size)
            .withDefaultTileset(tileset)
            .withDebugMode(true)
            .build())

    val random = Random()
    val terminalWidth = size.width
    val terminalHeight = size.height
    val layerCount = 20
    val layerWidth = 20
    val layerHeight = 10
    val layerSize = Size.create(layerWidth, layerHeight)
    val filler = Tile.defaultTile().withCharacter('x')

    val layers = (0..layerCount).map {

        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .withSize(layerSize)
                .withTileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.draw(filler, it)
        }

        val layer = LayerBuilder.newBuilder()
                .withOffset(Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)))
                .withTileGraphics(imageLayer)
                .build()

        tileGrid.addLayer(layer)
        layer
    }

    val tiles = listOf(
            Tile.newBuilder().withCharacter('a').withStyleSet(StyleSet.create(
                    foregroundColor = ANSITileColor.YELLOW,
                    backgroundColor = ANSITileColor.BLUE))
                    .buildCharacterTile(),
            Tile.newBuilder().withCharacter('b').withStyleSet(StyleSet.create(
                    foregroundColor = ANSITileColor.GREEN,
                    backgroundColor = ANSITileColor.RED))
                    .buildCharacterTile())


    var currIdx = 0

    while (true) {
        fillGrid(tileGrid, tiles[currIdx])
        layers.forEach {
            it.moveTo(Position.create(
                    x = random.nextInt(terminalWidth - layerWidth),
                    y = random.nextInt(terminalHeight - layerHeight)))
        }
        currIdx = if (currIdx == 0) 1 else 0
    }
}


private fun fillGrid(tileGrid: TileGrid, tile: Tile) {
    (0..tileGrid.size.height).forEach { y ->
        (0..tileGrid.size.width).forEach { x ->
            tileGrid.draw(tile, GridPosition(x, y))
        }
    }
}
