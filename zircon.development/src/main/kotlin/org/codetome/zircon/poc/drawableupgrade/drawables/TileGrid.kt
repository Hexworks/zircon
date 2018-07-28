package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

interface TileGrid<T: Any, S: Any> : DrawSurface<T>, Layerable<S> {

    fun getColumnCount(): Int

    fun getRowCount(): Int

    fun tileset(): Tileset<T, S>
}
