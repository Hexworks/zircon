package org.codetome.zircon.poc.drawableupgrade.renderer

import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

class StringAppendableRenderer(private val appendable: Appendable,
                               private val tileset: Tileset<String>) : Renderer<String> {

    override fun render(grid: TileGrid) {
        val tiles = grid.createSnapshot().toSortedMap()
        var lastPosition = tiles.firstKey()
        tiles.forEach { pos, tile ->
            if (pos.y > lastPosition.y) {
                appendable.appendln()
            }
            appendable.append("${tileset.fetchTextureForTile(tile).getTexture()} ")
            lastPosition = pos
        }
    }
}
