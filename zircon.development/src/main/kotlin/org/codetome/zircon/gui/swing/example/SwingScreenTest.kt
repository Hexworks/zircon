package org.codetome.zircon.gui.swing.example

import org.codetome.zircon.Stats
import org.codetome.zircon.api.data.*
import org.codetome.zircon.api.graphics.Symbols
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.graphics.DefaultLayer
import org.codetome.zircon.internal.graphics.MapTileImage
import org.codetome.zircon.internal.grid.RectangleTileGrid
import org.codetome.zircon.internal.screen.TileGridScreen
import org.codetome.zircon.gui.swing.impl.BufferedImageCP437Tileset
import org.codetome.zircon.gui.swing.impl.SwingFrame
import java.awt.image.BufferedImage
import java.util.*

fun main(args: Array<String>) {

    val size = Size.create(70, 40)
    val tileset = BufferedImageCP437Tileset.rexPaint16x16()
    val tileGrid: TileGrid<Char, BufferedImage> = RectangleTileGrid(tileset, size)
    val frame = SwingFrame(tileGrid)
    val screen0 = TileGridScreen(tileGrid)
    val screen1 = TileGridScreen(tileGrid)

    val random = Random()
    val terminalWidth = size.xLength
    val terminalHeight = size.yLength
    val layerCount = 20
    val layerWidth = 15
    val layerHeight = 15
    val layerSize = Size.create(layerWidth, layerHeight)

    val gridChars = listOf(
            CharacterTile('a'),
            CharacterTile('b'))
    val layerChars = listOf(
            CharacterTile(Symbols.BLOCK_SOLID),
            CharacterTile(Symbols.BLOCK_SPARSE))
    val screens = listOf(screen0, screen1)

    var currIdx = 0
    var loopCount = 0

    frame.isVisible = true

    frame.renderer.create()

    gridChars.forEachIndexed { idx, char ->
        fillGrid(screens[idx], char)
        (0..layerCount).map {
            val imageLayer = MapTileImage(layerSize, tileset)
            layerSize.fetchPositions().forEach {
                imageLayer.setTileAt(it, layerChars[idx])
            }

            val layer = DefaultLayer(
                    position = Position.create(
                            x = random.nextInt(terminalWidth - layerWidth),
                            y = random.nextInt(terminalHeight - layerHeight)),
                    backend = imageLayer)

            screens[idx].pushLayer(layer)
            layer
        }
    }


    while (true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            screens[currIdx].display()
            frame.renderer.render()
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
