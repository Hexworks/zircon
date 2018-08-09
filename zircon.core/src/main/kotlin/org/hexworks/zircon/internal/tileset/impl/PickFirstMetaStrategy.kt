package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTextureMetadata
import org.hexworks.zircon.api.tileset.MetadataPickingStrategy

class PickFirstMetaStrategy<T: Tile> : MetadataPickingStrategy<T> {

    override fun pickMetadata(metas: List<TileTextureMetadata<T>>): TileTextureMetadata<T> {
        return metas.first()
    }
}
