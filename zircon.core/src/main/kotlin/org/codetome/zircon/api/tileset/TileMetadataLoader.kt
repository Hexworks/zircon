package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile

interface TileMetadataLoader<T: Tile> {

    fun supportsTile(tile: Tile): Boolean

    fun fetchMetaForTile(tile: T): TileTextureMetadata<T>
}
