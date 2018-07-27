package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.util.Random
import org.codetome.zircon.api.tileset.MetadataPickingStrategy

class PickRandomMetaStrategy : MetadataPickingStrategy {

    private val random = Random.create()

    override fun pickMetadata(metas: List<TileTextureMetadata>): TileTextureMetadata {
        return metas[random.nextInt(metas.size)]
    }
}
