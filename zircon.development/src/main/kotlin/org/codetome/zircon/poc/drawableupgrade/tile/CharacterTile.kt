package org.codetome.zircon.poc.drawableupgrade.tile

import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable
import org.codetome.zircon.api.data.Position

data class CharacterTile(override val type: Char) : Drawable<Char>, Tile<Char> {

    override fun drawOnto(surface: DrawSurface<Char>, offset: Position) {
        surface.setTileAt(offset, this)
    }
}
