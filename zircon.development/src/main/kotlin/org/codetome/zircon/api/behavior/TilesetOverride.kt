package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.tileset.Tileset

interface TilesetOverride<T: Any, S: Any> {

    fun tileset(): Tileset<T, S>

    fun useTileset(tileset: Tileset<T, S>)
}
