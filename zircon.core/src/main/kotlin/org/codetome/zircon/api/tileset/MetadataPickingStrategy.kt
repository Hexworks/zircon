package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile

interface MetadataPickingStrategy<T : Tile> {

    fun pickMetadata(metas: List<TileTextureMetadata<T>>): TileTextureMetadata<T>
}
