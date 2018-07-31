package org.codetome.zircon.api.grid

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.tileset.Tileset

interface TileGrid<T: Any, S: Any>
    : Closeable, Clearable, DrawSurface<T>, Layerable, TilesetOverride<T, S>, Styleable {

    fun widthInPixels() = tileset().width() * getBoundableSize().xLength

    fun heightInPixels() = tileset().height() * getBoundableSize().yLength

}
