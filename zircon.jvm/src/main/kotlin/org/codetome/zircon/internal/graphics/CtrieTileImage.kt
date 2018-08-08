package org.codetome.zircon.internal.graphics

import com.romix.scala.collection.concurrent.TrieMap
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.resource.TilesetResource

class CtrieTileImage(
        size: Size,
        tileset: TilesetResource<out Tile>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage(
        tileset = tileset,
        contents = TrieMap<Position, Tile>(),
        styleSet = styleSet,
        size = size)
