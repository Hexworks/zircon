package org.codetome.zircon.poc.drawableupgrade.renderer

import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

class StringAppendableRenderer(override val surface: Appendable,
                               private val tileset: Tileset<String, String>,
                               private val grid: TileGrid<String, String>) : Renderer<Appendable> {

    override fun render() {
        val tiles = grid.createSnapshot().toSortedMap()
        var lastPosition = tiles.firstKey()
        tiles.forEach { pos, tile ->
            if (pos.y > lastPosition.y) {
                surface.appendln()
            }
            surface.append("${tileset.fetchTextureForTile(tile).getTexture()} ")
            lastPosition = pos
        }
    }
}
