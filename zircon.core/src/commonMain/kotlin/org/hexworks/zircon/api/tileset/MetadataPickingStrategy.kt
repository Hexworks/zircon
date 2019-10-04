package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

interface MetadataPickingStrategy<T : Tile> {

    fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T>
}
