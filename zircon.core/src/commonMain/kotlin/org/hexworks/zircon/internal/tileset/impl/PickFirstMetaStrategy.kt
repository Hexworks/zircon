package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.MetadataPickingStrategy
import org.hexworks.zircon.api.tileset.TextureMetadata

class PickFirstMetaStrategy<T : Tile> : MetadataPickingStrategy<T> {

    override fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T> {
        return metas.first()
    }
}
