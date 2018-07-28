package org.codetome.zircon.poc.drawableupgrade.tile

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.TilesetOverride
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

data class ImageTile<S: Any>(override val key: String,
                             private val tileset: Tileset<String, S>) : Tile<String>, TilesetOverride<String, S> {

    override fun tileset() = tileset

    override fun drawOnto(surface: DrawSurface<String>, offset: GridPosition) {
        surface.setTileAt(offset, this)
    }
}
