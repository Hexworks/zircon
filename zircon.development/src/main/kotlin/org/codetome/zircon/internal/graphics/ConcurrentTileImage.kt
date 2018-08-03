package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.resource.TilesetResource
import java.util.concurrent.ConcurrentHashMap

class ConcurrentTileImage(
        size: Size,
        tileset: TilesetResource<out Tile>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage(
        size = size,
        tileset = tileset,
        contents = ConcurrentHashMap(),
        styleSet = styleSet)
