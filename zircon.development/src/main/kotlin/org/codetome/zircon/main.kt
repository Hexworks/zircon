package org.codetome.zircon

import org.codetome.zircon.poc.drawableupgrade.Position
import org.codetome.zircon.poc.drawableupgrade.RectangleTileGrid
import org.codetome.zircon.poc.drawableupgrade.Tile
import org.codetome.zircon.poc.drawableupgrade.drawables.MapTileImage
import org.codetome.zircon.poc.drawableupgrade.drawables.ThreadedTileImage
import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.renderer.NoOpAppendable
import org.codetome.zircon.poc.drawableupgrade.renderer.Renderer
import org.codetome.zircon.poc.drawableupgrade.renderer.StringAppendableRenderer
import org.codetome.zircon.poc.drawableupgrade.renderer.StringTerminalRenderer
import org.codetome.zircon.poc.drawableupgrade.tileset.StringTileset
import java.util.*

fun main(args: Array<String>) {

    testRender()

    System.exit(0)

}

private fun testRender() {
    val width = 20
    val height = 10
    val imageWidth = 5
    val imageHeight = 5
    val renderer: Renderer<out Any> = StringTerminalRenderer(StringTileset)
    val tileGrid: TileGrid = RectangleTileGrid(width, height)

    val image = MapTileImage(imageWidth, imageHeight)
    (0..imageHeight).forEach { y ->
        (0..imageWidth).forEach { x ->
            image.setTileAt(Position(x, y), Tile('x'))
        }
    }

    (0..height).forEach { y ->
        (0..width).forEach { x ->
            tileGrid.setTileAt(Position(x, y), Tile('_'))
        }
    }

    tileGrid.draw(image, Position(2, 3))
    renderer.render(tileGrid)

    val rnd = Random()
    var loopCount = 0

    while (false) {
        if (loopCount.rem(1000) == 0) {
            Stats.printStats()
        }
        Stats.addTimedStatFor("RenderBenchmark") {
            (0..20).forEach {
                tileGrid.draw(image, Position(rnd.nextInt(80), rnd.nextInt(40)))
            }
            renderer.render(tileGrid)
        }
        loopCount++
    }
}
