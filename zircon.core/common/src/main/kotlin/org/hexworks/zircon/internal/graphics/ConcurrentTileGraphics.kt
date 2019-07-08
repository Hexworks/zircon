package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory

class ConcurrentTileGraphics(
        size: Size,
        tileset: TilesetResource)
    : BaseTileGraphics(
        tileset = tileset,
        initialSize = size,
        contents = ThreadSafeMapFactory.create())
