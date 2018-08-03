package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.MetadataPickingStrategy

class PickFirstMetaStrategy<T: Tile> : MetadataPickingStrategy<T> {

    override fun pickMetadata(metas: List<TileTextureMetadata<T>>): TileTextureMetadata<T> {
        return metas.first()
    }
}
