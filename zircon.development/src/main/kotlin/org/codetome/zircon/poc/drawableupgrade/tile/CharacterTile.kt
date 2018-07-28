package org.codetome.zircon.poc.drawableupgrade.tile

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable

data class CharacterTile(override val key: Char) : Drawable<Char>, Tile<Char> {

    override fun drawOnto(surface: DrawSurface<Char>, offset: GridPosition) {
        surface.setTileAt(offset, this)
    }
}
