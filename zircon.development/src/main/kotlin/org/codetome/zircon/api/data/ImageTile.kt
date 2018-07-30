package org.codetome.zircon.api.data

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.tileset.Tileset

data class ImageTile<S : Any>(override val type: String,
                              private var tileset: Tileset<String, S>,
                              private val style: StyleSet = StyleSet.defaultStyle())
    : Tile<String>, TilesetOverride<String, S> {

    override fun getForegroundColor() = style.getForegroundColor()

    override fun getBackgroundColor() = style.getBackgroundColor()

    override fun getModifiers() = style.getModifiers()

    override fun tileset() = tileset

    override fun useTileset(tileset: Tileset<String, S>) {
        this.tileset = tileset
    }

    override fun drawOnto(surface: DrawSurface<String>, offset: Position) {
        surface.setTileAt(offset, this)
    }
}
