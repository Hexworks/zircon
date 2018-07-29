package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

interface TilesetOverride<T: Any, S: Any> {

    fun tileset(): Tileset<T, S>

    fun useTileset(tileset: Tileset<T, S>)
}
