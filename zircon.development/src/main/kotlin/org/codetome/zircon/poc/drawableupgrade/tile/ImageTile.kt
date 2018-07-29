package org.codetome.zircon.poc.drawableupgrade.tile

import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.TilesetOverride
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

data class ImageTile<S: Any>(override val type: String,
                             private var tileset: Tileset<String, S>) : Tile<String>, TilesetOverride<String, S> {

    override fun tileset() = tileset

    override fun useTileset(tileset: Tileset<String, S>) {
        this.tileset = tileset
    }

    override fun drawOnto(surface: DrawSurface<String>, offset: Position) {
        surface.setTileAt(offset, this)
    }
}
