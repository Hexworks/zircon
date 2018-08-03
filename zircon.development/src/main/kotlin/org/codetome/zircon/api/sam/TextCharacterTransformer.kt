package org.codetome.zircon.api.sam

import org.codetome.zircon.api.data.Tile

interface TextCharacterTransformer {

    fun transform(tc: Tile): Tile
}
