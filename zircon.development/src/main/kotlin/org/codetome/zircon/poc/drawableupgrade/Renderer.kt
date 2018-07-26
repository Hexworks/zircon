package org.codetome.zircon.poc.drawableupgrade

class Renderer {

    // This is just some dummy rendering for testing purposes.
    // In real life this would use things like a `BufferedImage` which contains
    // the sprites for each `Tile` and such. Now we just dump the contents of the
    // grid to stdout.

    fun render(grid: TileGrid) {
        // we need to sort it so we can draw in order
        // Position is comparable, that helps
        val tiles = grid.createSnapshot().toSortedMap()
        var lastPosition = tiles.firstKey()
        tiles.forEach { pos, tile ->
            if(pos.y > lastPosition.y) {
                println()
            }
            print("${tile.char} ")
            lastPosition = pos
        }
    }
}
