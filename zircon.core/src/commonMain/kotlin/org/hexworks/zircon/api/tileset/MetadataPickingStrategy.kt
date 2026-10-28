package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

//! TODO: document this
public fun interface MetadataPickingStrategy<T : Tile> {

    public fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T>
}
