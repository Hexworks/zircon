package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.GraphicalTile

/**
 * Where a [GraphicalTile] sits in its sprite sheet: [x] and [y] are tile indices, not
 * pixels, and [file] names the sheet inside the tileset archive.
 */
public data class GraphicalTextureMetadata(
    override val x: Int,
    override val y: Int,
    override val width: Int,
    override val height: Int,
    val name: String,
    val tags: Set<String>,
    val file: String,
) : TextureMetadata<GraphicalTile>
