package org.codetome.zircon

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.RectangleTileGrid
import org.codetome.zircon.poc.drawableupgrade.Symbols
import org.codetome.zircon.poc.drawableupgrade.tile.CharacterTile
import org.codetome.zircon.poc.drawableupgrade.tileimage.MapTileImage
import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.misc.SwingFrame
import org.codetome.zircon.poc.drawableupgrade.renderer.SwingCanvasRenderer
import org.codetome.zircon.poc.drawableupgrade.tile.ImageTile
import org.codetome.zircon.poc.drawableupgrade.tileimage.DefaultLayer
import org.codetome.zircon.poc.drawableupgrade.tileset.BufferedImageCP437Tileset
import org.codetome.zircon.poc.drawableupgrade.tileset.BufferedImageDictionaryTileset
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
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
    val imageWidth = 5
    val imageHeight = 5
    val imageCount = 20
    val tileset = BufferedImageCP437Tileset.rexPaint18x18()
    val layerTileset: Tileset<Char, BufferedImage> = BufferedImageCP437Tileset.rexPaint16x16()
    val imageTileset: Tileset<String, BufferedImage> = BufferedImageDictionaryTileset.fromResourceDir()
    val tileGrid: TileGrid<Char, BufferedImage> = RectangleTileGrid(width, height, tileset)
    val canvas = Canvas()
    val renderer = SwingCanvasRenderer(canvas, tileGrid)
    val frame = SwingFrame(renderer)



    val image = MapTileImage(imageWidth, imageHeight, layerTileset)
    val imageTile = ImageTile("super_mario.png", imageTileset)
    val imageLayer = MapTileImage(1, 1, imageTileset)
    imageLayer.setTileAt(GridPosition(0, 0), imageTile)
    (0..imageHeight).forEach { y ->
        (0..imageWidth).forEach { x ->
            image.setTileAt(GridPosition(x, y), CharacterTile(Symbols.BLOCK_DENSE))
        }
    }

    (0..height).forEach { y ->
        (0..width).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), CharacterTile(Symbols.BLOCK_SPARSE))
        }
    }

    frame.isVisible = true

//    tileGrid.setTileAt(GridPosition(1, 1), imageTile)
    tileGrid.pushLayer(DefaultLayer(GridPosition(2, 3), image))
    tileGrid.pushLayer(DefaultLayer(GridPosition(1, 1), imageLayer))
    renderer.render()

    val rnd = Random()
    var loopCount = 0

    Thread.sleep(5000)

//    while (false) {
//        if (loopCount.rem(1000) == 0) {
//            Stats.printStats()
//        }
//        Stats.addTimedStatFor("RenderBenchmark") {
//            (0..imageCount).forEach {
//                tileGrid.draw(image, Position(rnd.nextInt(80), rnd.nextInt(40)))
//            }
//            renderer.render()
//        }
//        loopCount++
//    }
}
