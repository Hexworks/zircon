package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.util.Random
import org.codetome.zircon.api.tileset.MetadataPickingStrategy

class PickRandomMetaStrategy<T: Tile> : MetadataPickingStrategy<T> {

    private val random = Random.create()

    override fun pickMetadata(metas: List<TileTextureMetadata<T>>): TileTextureMetadata<T> {
        return metas[random.nextInt(metas.size)]
    }
}
