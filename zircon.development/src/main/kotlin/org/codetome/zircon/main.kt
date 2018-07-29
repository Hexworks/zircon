package org.codetome.zircon

import org.codetome.zircon.api.data.GridPosition
import org.codetome.zircon.poc.drawableupgrade.grid.RectangleTileGrid
import org.codetome.zircon.poc.drawableupgrade.Symbols
import org.codetome.zircon.poc.drawableupgrade.tile.CharacterTile
import org.codetome.zircon.poc.drawableupgrade.tileimage.MapTileImage
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.poc.drawableupgrade.misc.SwingFrame
import org.codetome.zircon.api.data.AbsolutePosition
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.poc.drawableupgrade.renderer.SwingCanvasRenderer
import org.codetome.zircon.poc.drawableupgrade.tile.ImageTile
import org.codetome.zircon.poc.drawableupgrade.tileimage.DefaultLayer
import org.codetome.zircon.poc.drawableupgrade.tileset.BufferedImageCP437Tileset
import org.codetome.zircon.poc.drawableupgrade.tileset.BufferedImageDictionaryTileset
import java.awt.Canvas
import java.awt.image.BufferedImage
import java.util.*

fun main(args: Array<String>) {

    testRender()

    System.exit(0)

}

private fun testRender() {
    val width = 50
    val height = 30
    val imageWidth = 4
    val imageHeight = 4
    val imageCount = 20
    val tileset = BufferedImageCP437Tileset.rexPaint16x16()
    val layerTileset = BufferedImageCP437Tileset.rexPaint8x8()
    val imageTileset = BufferedImageDictionaryTileset.fromResourceDir()
    val tileGrid: TileGrid<Char, BufferedImage> = RectangleTileGrid(Size.create(width, height), tileset)
    val canvas = Canvas()
    val renderer = SwingCanvasRenderer(canvas, tileGrid)
    val frame = SwingFrame(renderer)



    val image = MapTileImage(Size.create(imageWidth, imageHeight), layerTileset)
    val imageTile = ImageTile("hexworks_logo.png", imageTileset)
    val imageLayer = MapTileImage(Size.create(1, 1), imageTileset)
    imageLayer.setTileAt(GridPosition(0, 0), imageTile)
    (0..imageHeight).forEach { y ->
        (0..imageWidth).forEach { x ->
            image.setTileAt(GridPosition(x, y), CharacterTile(Symbols.BLOCK_DENSE))
        }
    }

    fillGrid(tileGrid)

    frame.isVisible = true

    tileGrid.pushLayer(DefaultLayer(GridPosition(1, 1), image))
    tileGrid.pushLayer(DefaultLayer(AbsolutePosition(56, 93), imageLayer))
    renderer.render()

    val rnd = Random()
    var loopCount = 0

//    Thread.sleep(5000)

    while (false) {
        if (loopCount.rem(1000) == 0) {
            Stats.printStats()
            tileGrid.clear()
            fillGrid(tileGrid)
        }
        Stats.addTimedStatFor("RenderBenchmark") {
            (0..imageCount).forEach {
                tileGrid.draw(image, Position.create(
                        x = rnd.nextInt(width - imageWidth),
                        y = rnd.nextInt(height - imageHeight)))
            }
            renderer.render()
        }
        loopCount++
    }
}

private fun fillGrid(tileGrid: TileGrid<Char, BufferedImage>) {
    (0..tileGrid.getBoundableSize().yLength).forEach { y ->
        (0..tileGrid.getBoundableSize().xLength).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), CharacterTile(Symbols.SINGLE_LINE_CROSS))
        }
    }
}
