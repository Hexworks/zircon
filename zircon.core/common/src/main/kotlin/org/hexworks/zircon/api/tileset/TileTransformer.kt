package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier

interface TileTransformer<T : TileTransformModifier<U>, U : Tile> {

    fun transform(tile: U, modifier: T): U

    fun canTransform(tile: Tile): Boolean
}
