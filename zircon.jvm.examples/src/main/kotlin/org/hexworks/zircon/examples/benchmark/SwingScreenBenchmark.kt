package org.hexworks.zircon.examples.benchmark

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.GridPosition
import org.hexworks.zircon.api.grid.TileGrid
import java.util.*

fun main(args: Array<String>) {

    val size = Size.create(80, 40)

    val tileset = CP437TilesetResources.wanderlust16x16()

    val screen = Screens.createScreenFor(SwingApplications.startTileGrid(AppConfigBuilder.newBuilder()
            .withSize(size)
            .withDefaultTileset(tileset)
            .withDebugMode(true)
            .build()))

    screen.display()

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
                .withSize(layerSize)
                .withTileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = LayerBuilder.newBuilder()
                .withOffset(Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)))
                .withTileGraphics(imageLayer)
                .build()

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
    (0..tileGrid.size.height).forEach { y ->
        (0..tileGrid.size.width).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
