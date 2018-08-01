package org.codetome.zircon.api.sam

import org.codetome.zircon.api.data.Tile

interface TextCharacterTransformer<T: Any> {

    fun transform(tc: Tile<T>): Tile<T>
}
