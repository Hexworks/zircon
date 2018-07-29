package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
import java.util.concurrent.ConcurrentHashMap

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

class MapTileImage<T : Any, S : Any>(
        size: Size,
        tileset: Tileset<T, S>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage<T, S>(
        tileset = tileset,
        contents = ConcurrentHashMap<Position, Tile<T>>(),
        styleSet = styleSet,
        size = size)
