package org.codetome.zircon.api.tileset

import org.codetome.zircon.internal.tileset.impl.DefaultTileTextureMetadata

/**
 * Metadata about a [TileTexture], like `tags` and its position (x, y) in a [Tileset].
 */
interface TileTextureMetadata {

    val char: Char
    val x: Int
    val y: Int
    val tags: Set<String>

    companion object {

        fun create(char: Char,
                   x: Int,
                   y: Int,
                   tags: Set<String> = setOf()) = DefaultTileTextureMetadata(
                char = char,
                tags = tags,
                x = x,
                y = y)
    }
}
