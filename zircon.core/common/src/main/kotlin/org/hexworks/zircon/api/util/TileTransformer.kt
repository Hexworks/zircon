package org.hexworks.zircon.api.util

import org.hexworks.zircon.api.data.Tile

interface TileTransformer {

    fun transform(tc: Tile): Tile
}
