package org.codetome.zircon.api.data

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable

data class CharacterTile(override val type: Char,
                         private val style: StyleSet = StyleSet.defaultStyle())
    : Drawable<Char>, Tile<Char> {

    override fun getForegroundColor() = style.getForegroundColor()

    override fun getBackgroundColor() = style.getBackgroundColor()

    override fun getModifiers() = style.getModifiers()

    override fun drawOnto(surface: DrawSurface<Char>, offset: Position) {
        surface.setTileAt(offset, this)
    }
}
