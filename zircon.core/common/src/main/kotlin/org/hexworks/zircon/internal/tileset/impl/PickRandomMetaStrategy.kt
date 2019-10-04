package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.MetadataPickingStrategy
import org.hexworks.zircon.api.tileset.TextureMetadata
import kotlin.random.Random

class PickRandomMetaStrategy<T : Tile> : MetadataPickingStrategy<T> {

    private val random = Random(23425632)

    override fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T> {
        return metas[random.nextInt(metas.size)]
    }
}
