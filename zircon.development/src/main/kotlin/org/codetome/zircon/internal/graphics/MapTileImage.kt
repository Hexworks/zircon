package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import java.util.concurrent.ConcurrentHashMap

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

class MapTileImage(
        size: Size,
        tileset: TilesetResource<out Tile>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage(
        tileset = tileset,
        contents = ConcurrentHashMap(),
        styleSet = styleSet,
        size = size)
