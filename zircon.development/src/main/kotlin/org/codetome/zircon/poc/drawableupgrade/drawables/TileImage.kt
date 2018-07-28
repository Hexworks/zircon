package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

interface TileImage<T : Any, S: Any> : DrawSurface<T>, Drawable<T> {

    fun tileset(): Tileset<T, S>
}
