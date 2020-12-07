package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

/**
 * Metadata about a [TileTexture], like its position (x, y) in a [Tileset].
 */
interface TextureMetadata<T : Tile> {
    val x: Int
    val y: Int
    val width: Int
    val height: Int
}
