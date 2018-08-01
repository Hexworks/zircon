package org.codetome.zircon.api.tileset

interface MetadataPickingStrategy {

    fun pickMetadata(metas: List<TileTextureMetadata>) : TileTextureMetadata
}
