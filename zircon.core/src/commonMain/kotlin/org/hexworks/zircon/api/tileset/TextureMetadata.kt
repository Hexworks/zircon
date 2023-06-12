package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

/**
 * Holds the details of a [TextureContext] (position, dimensions).
 */
interface TextureMetadata<T : Tile> {
    val x: Int
    val y: Int
    val width: Int
    val height: Int
}
