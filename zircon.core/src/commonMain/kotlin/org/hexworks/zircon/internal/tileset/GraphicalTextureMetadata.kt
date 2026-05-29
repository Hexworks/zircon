package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.data.tile.ImageTile
import org.hexworks.zircon.api.tileset.TextureMetadata

data class GraphicalTextureMetadata(
    override val x: Int,
    override val y: Int,
    override val width: Int,
    override val height: Int,
    val name: String,
    val tags: Set<String>
) : TextureMetadata<ImageTile>
