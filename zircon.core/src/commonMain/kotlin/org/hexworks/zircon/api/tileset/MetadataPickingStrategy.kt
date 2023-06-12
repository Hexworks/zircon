package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

// TODO: document this
fun interface MetadataPickingStrategy<T : Tile> {

    fun pickMetadata(metas: List<TextureMetadata<T>>): TextureMetadata<T>
}
