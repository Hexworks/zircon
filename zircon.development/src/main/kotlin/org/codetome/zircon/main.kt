package org.codetome.zircon

import org.codetome.zircon.poc.drawableupgrade.*

fun main(args: Array<String>) {

    testRender()

    System.exit(0)

}

private fun testRender() {
    val width = 20
    val height = 10
    val renderer = Renderer()
    val tileGrid = TileGrid(width, height)

    val tile = Tile('y')
    val image = TileImage(3, 3)
    (0..3).forEach { y ->
        (0..3).forEach { x ->
            image.setTileAt(Position(x, y), Tile('x'))
        }
    }

    val otherImage = TileImage(2, 1)
    otherImage.setTileAt(Position(0, 0), Tile('z'))
    otherImage.setTileAt(Position(1, 0), Tile('z'))

    image.draw(otherImage, Position(1, 1))

    (0..height).forEach { y ->
        (0..width).forEach { x ->
            tileGrid.setTileAt(Position(x, y), Tile('_'))
        }
    }

    renderer.render(tileGrid)
    println()
    println()

    tileGrid.draw(image, Position(4, 5))

    renderer.render(tileGrid)
    println()
    println()

    tileGrid.draw(tile, Position(15, 2))

    renderer.render(tileGrid)
    println()
    println()

    tileGrid.setTileAt(Position(8, 9), tile)

    renderer.render(tileGrid)
}
