package org.codetome.zircon.internal.tileset

import org.codetome.zircon.api.tileset.TileTextureMetadata

interface MetadataPickingStrategy {

    fun pickMetadata(metas: List<TileTextureMetadata>) : TileTextureMetadata
}
