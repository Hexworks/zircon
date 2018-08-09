package org.hexworks.zircon.internal.graphics

import com.romix.scala.collection.concurrent.TrieMap
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BaseTileGraphic
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource

class CtrieTileGraphic(
        size: Size,
        tileset: TilesetResource,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileGraphic(
        tileset = tileset,
        contents = TrieMap<Position, Tile>(),
        styleSet = styleSet,
        size = size)
