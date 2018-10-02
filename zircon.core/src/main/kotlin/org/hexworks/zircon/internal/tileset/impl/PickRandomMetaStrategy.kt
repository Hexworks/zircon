package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureMetadata
import org.hexworks.zircon.api.util.Random
import org.hexworks.zircon.api.tileset.MetadataPickingStrategy

class PickRandomMetaStrategy<T: Tile> : MetadataPickingStrategy<T> {

    private val random = Random.create()

    override fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T> {
        return metas[random.nextInt(metas.size)]
    }
}
