package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TileTextureMetadata
import org.codetome.zircon.api.tileset.MetadataPickingStrategy

class PickFirstMetaStrategy : MetadataPickingStrategy {

    override fun pickMetadata(metas: List<TileTextureMetadata>): TileTextureMetadata {
        return metas.first()
    }
}
