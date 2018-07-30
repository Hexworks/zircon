package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.tileset.Tileset
import java.util.concurrent.ConcurrentHashMap

class ConcurrentTileImage<T : Any, S : Any>(
        size: Size,
        tileset: Tileset<T, S>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage<T, S>(
        size = size,
        tileset = tileset,
        contents = ConcurrentHashMap<Position, Tile<T>>(),
        styleSet = styleSet)
