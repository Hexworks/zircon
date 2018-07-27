package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImageBase

class MapTileImage(size: Size,
                   styleSet: StyleSet = StyleSet.defaultStyle(),
                   tiles: Map<Position, Tile> = mapOf(),
                   private val backend: MutableMap<Position, Tile> =
                           mutableMapOf())
    : TileImageBase(size = size,
        styleSet = styleSet,
        backend = backend,
        tiles = tiles)
