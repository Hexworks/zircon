package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

/**
 * Holds the details of a [TextureContext] (position, dimensions).
 */
public interface TextureMetadata<T : Tile> {
    public val x: Int
    public val y: Int
    public val width: Int
    public val height: Int
}
