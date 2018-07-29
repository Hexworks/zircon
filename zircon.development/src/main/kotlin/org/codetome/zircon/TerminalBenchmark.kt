package org.codetome.zircon

import org.codetome.zircon.api.data.GridPosition
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.poc.drawableupgrade.grid.RectangleTileGrid
import org.codetome.zircon.poc.drawableupgrade.misc.SwingFrame
import org.codetome.zircon.poc.drawableupgrade.renderer.SwingCanvasRenderer
import org.codetome.zircon.poc.drawableupgrade.tile.CharacterTile
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileimage.DefaultLayer
import org.codetome.zircon.poc.drawableupgrade.tileimage.MapTileImage
import org.codetome.zircon.poc.drawableupgrade.tileset.Java2DCP437Tileset
import java.awt.Canvas
import java.awt.image.BufferedImage
import java.awt.image.VolatileImage
import java.util.*

val SIZE = Size.create(70, 40)

fun main(args: Array<String>) {

    val tileset = Java2DCP437Tileset.rexPaint16x16()

    val tileGrid: TileGrid<Char, VolatileImage> = RectangleTileGrid(tileset, SIZE)
    val canvas = Canvas()
    val renderer = SwingCanvasRenderer(canvas, tileGrid)
    val frame = SwingFrame(renderer)


    val random = Random()
    val terminalWidth = SIZE.xLength
    val terminalHeight = SIZE.yLength
    val layerCount = 20
    val layerWidth = 15
    val layerHeight = 15
    val layerSize = Size.create(layerWidth, layerHeight)
    var layers = (0..layerCount).map {
        val filler = CharacterTile('x')

        val imageLayer = MapTileImage(layerSize, tileset)
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = DefaultLayer(
                position = Position.create(random.nextInt(terminalWidth - layerWidth), random.nextInt(terminalHeight - layerHeight)),
                backend = imageLayer)

        tileGrid.pushLayer(layer)
        layer
    }

    val chars = listOf('a', 'b')

    var currIdx = 0
    var loopCount = 0

    frame.isVisible = true

    while (true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            val tile = CharacterTile(chars[currIdx])
            fillGrid(tileGrid, tile)
            layers.forEach {
                tileGrid.removeLayer(it)
            }
            val filler = CharacterTile('x')
            layers = (0..layerCount).map {

                val imageLayer = MapTileImage(layerSize, tileset)
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
            renderer.render()
            renderer.clear()
            currIdx = if (currIdx == 0) 1 else 0
            loopCount++
        }
        if (loopCount.rem(100) == 0) {
            Stats.printStats()
        }
    }
}

private fun fillGrid(tileGrid: TileGrid<Char, out Any>, tile: Tile<Char>) {
    (0..tileGrid.getBoundableSize().yLength).forEach { y ->
        (0..tileGrid.getBoundableSize().xLength).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
