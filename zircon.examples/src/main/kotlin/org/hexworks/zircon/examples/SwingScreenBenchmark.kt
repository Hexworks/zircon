package org.hexworks.zircon.examples

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.data.GridPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.internal.graphics.DefaultLayer
import org.hexworks.zircon.internal.graphics.MapTileGraphic
import org.hexworks.zircon.internal.screen.TileGridScreen
import java.util.*

fun main(args: Array<String>) {

    val size = Size.create(80, 40)

    val tileset = CP437TilesetResource.WANDERLUST_16X16

    val screen = TileGridScreen(SwingApplications.startTileGrid(AppConfigBuilder.newBuilder()
            .defaultSize(size)
            .defaultTileset(tileset)
            .debugMode(true)
            .build()))

    screen.display()

    val random = Random()
    val terminalWidth = size.xLength
    val terminalHeight = size.yLength
    val layerCount = 20
    val layerWidth = 20
    val layerHeight = 10
    val layerSize = Size.create(layerWidth, layerHeight)
    val filler = Tiles.defaultTile().withCharacter('x')

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

        screen.pushLayer(layer)
        layer
    }

    val chars = listOf('a', 'b')

    var currIdx = 0


    while (true) {
        val tile = Tiles.defaultTile().withCharacter(chars[currIdx])
        fillGrid(screen, tile)
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
