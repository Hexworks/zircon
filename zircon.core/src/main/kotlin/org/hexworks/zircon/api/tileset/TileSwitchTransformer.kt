package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileSwitchModifier

interface TileSwitchTransformer<T : TileSwitchModifier> {

    fun transform(tile: Tile, modifier: T): Tile
}
