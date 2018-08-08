package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile

/**
 * Metadata about a [TileTexture], like `tags` and its position (x, y) in a [Tileset].
 */
interface TileTextureMetadata<T: Tile> {
    val x: Int
    val y: Int
    val width: Int
    val height: Int
}
