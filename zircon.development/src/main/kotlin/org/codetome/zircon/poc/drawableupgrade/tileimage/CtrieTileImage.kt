package org.codetome.zircon.poc.drawableupgrade.tileimage

import com.romix.scala.collection.concurrent.TrieMap
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

class CtrieTileImage<T : Any, S : Any>(
        size: Size,
        tileset: Tileset<T, S>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage<T, S>(
        tileset = tileset,
        contents = TrieMap<Position, Tile<T>>(),
        styleSet = styleSet,
        size = size)
