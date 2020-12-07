package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

interface TileMetadataLoader<T : Tile> {

    fun supportsTile(tile: Tile): Boolean

    fun fetchMetaForTile(tile: T): TextureMetadata<T>
}
