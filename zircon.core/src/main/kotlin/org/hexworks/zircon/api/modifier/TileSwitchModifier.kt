package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

interface TileSwitchModifier : Modifier {

    fun transform(tile: Tile): Tile
}
