package org.codetome.zircon.poc.drawableupgrade

import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable

/**
 * A [Tile] is the basic building block. In this case it just holds a character
 * but this can be any metadata in a real implementation which can be used by the renderer
 * to render an actual image (textures, fonts, anything).
 */
data class Tile(val char: Char) : Drawable {

    override fun drawOnto(surface: DrawSurface, offset: Position) {

        // this is the actual atomic draw operation when we
        // set a the a tile on a draw surface
        surface.setTileAt(offset, this)
    }
}
