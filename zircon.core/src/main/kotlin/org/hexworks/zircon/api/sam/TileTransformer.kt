package org.hexworks.zircon.api.sam

import org.hexworks.zircon.api.data.Tile

interface TileTransformer {

    fun transform(tc: Tile): Tile
}
