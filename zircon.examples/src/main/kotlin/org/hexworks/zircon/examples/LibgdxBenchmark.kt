package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.impl.GridPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.graphics.DefaultLayer
import java.util.*

fun main(args: Array<String>) {

    val size = Sizes.create(80, 40)

    val tileset = BuiltInCP437TilesetResource.WANDERLUST_16X16

    val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
            .defaultSize(size)
            .defaultTileset(tileset)
            .debugMode(true)
            .build())

    val random = Random()
    val terminalWidth = size.width
    val terminalHeight = size.height
    val layerCount = 20
    val layerWidth = 20
    val layerHeight = 10
    val layerSize = Size.create(layerWidth, layerHeight)
    val filler = Tiles.defaultTile().withCharacter('x')

    val layers = (0..layerCount).map {

        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .size(layerSize)
                .tileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = DefaultLayer(
                currentPosition = Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)),
                backend = imageLayer)

        tileGrid.pushLayer(layer)
        layer
    }

    val tiles = listOf(
            Tiles.newBuilder().character('a').styleSet(StyleSet.create(
                    foregroundColor = ANSITileColor.YELLOW,
                    backgroundColor = ANSITileColor.BLUE))
                    .buildCharacterTile(),
            Tiles.newBuilder().character('b').styleSet(StyleSet.create(
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
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
