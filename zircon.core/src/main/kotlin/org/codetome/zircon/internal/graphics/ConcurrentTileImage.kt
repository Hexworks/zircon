package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImageBase
import org.codetome.zircon.platform.factory.ThreadSafeMapFactory

class ConcurrentTileImage(size: Size,
                          styleSet: StyleSet = StyleSet.defaultStyle(),
                          tiles: Map<Position, Tile> = mapOf(),
                          private val backend: MutableMap<Position, Tile> =
                                  ThreadSafeMapFactory.create())
    : TileImageBase(size = size,
        styleSet = styleSet,
        backend = backend,
        tiles = tiles)
