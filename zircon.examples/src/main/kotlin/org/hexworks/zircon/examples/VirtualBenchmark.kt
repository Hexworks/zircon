package org.hexworks.zircon.examples

import org.hexworks.zircon.api.VirtualApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.internal.graphics.DefaultLayer
import org.hexworks.zircon.internal.graphics.MapTileGraphic
import java.util.*

fun main(args: Array<String>) {

    val size = Size.create(80, 40)

    val tileset = CP437TilesetResource.WANDERLUST_16X16

    val tileGrid = VirtualApplications.startTileGrid(AppConfigBuilder.newBuilder()
            .defaultSize(size)
            .defaultTileset(tileset)
            .debugMode(true)
            .build())

    val random = Random()
    val terminalWidth = size.xLength
    val terminalHeight = size.yLength
    val layerCount = 20
    val layerWidth = 20
    val layerHeight = 10
    val layerSize = Size.create(layerWidth, layerHeight)
    val filler = CharacterTile('x')

    val layers = (0..layerCount).map {

        val imageLayer = MapTileGraphic(layerSize, tileset)
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = DefaultLayer(
                position = Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)),
                backend = imageLayer)

        tileGrid.pushLayer(layer)
        layer
    }

    val tiles = listOf(
            CharacterTile('a', StyleSet.create(
                    foregroundColor = ANSITileColor.YELLOW,
                    backgroundColor = ANSITileColor.BLUE)),
            CharacterTile('b', StyleSet.create(
                    foregroundColor = ANSITileColor.GREEN,
                    backgroundColor = ANSITileColor.RED)))

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
    (0..tileGrid.size().yLength).forEach { y ->
        (0..tileGrid.size().xLength).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
