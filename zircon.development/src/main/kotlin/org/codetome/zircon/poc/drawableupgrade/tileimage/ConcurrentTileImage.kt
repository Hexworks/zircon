package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
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
