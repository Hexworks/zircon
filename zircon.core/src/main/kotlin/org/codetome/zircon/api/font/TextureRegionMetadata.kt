package org.codetome.zircon.api.font

import org.codetome.zircon.internal.font.DefaultTextureRegionMetadata

/**
 * Metadata about a [FontTextureRegion], like `tags` and its position (x, y) in a [Font].
 */
interface TextureRegionMetadata {

    val char: Char
    val x: Int
    val y: Int
    val tags: Set<String>

    companion object {

        fun create(char: Char,
                   x: Int,
                   y: Int,
                   tags: Set<String> = setOf()) = DefaultTextureRegionMetadata(
                char = char,
                tags = tags,
                x = x,
                y = y)
    }
}
