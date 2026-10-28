package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

// TODO: document this
public interface TileMetadataLoader<T : Tile> {

    public fun supportsTile(tile: Tile): Boolean

    public fun fetchMetaForTile(tile: T): TextureMetadata<T>
}
