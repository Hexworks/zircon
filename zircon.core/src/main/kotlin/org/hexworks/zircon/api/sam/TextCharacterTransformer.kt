package org.hexworks.zircon.api.sam

import org.hexworks.zircon.api.data.Tile

interface TextCharacterTransformer {

    fun transform(tc: Tile): Tile
}
