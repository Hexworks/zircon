package org.codetome.zircon.api.font

import org.codetome.zircon.internal.font.DefaultCharacterMetadata

/**
 * Metadata about a [Char], like `tags` and its position (x, y) in a [Font].
 */
interface CharacterMetadata {

    val char: Char
    val x: Int
    val y: Int
    val tags: Set<String>

    companion object {

        fun create(char: Char,
                   x: Int,
                   y: Int,
                   tags: Set<String> = setOf()) = DefaultCharacterMetadata(
                char = char,
                tags = tags,
                x = x,
                y = y)
    }
}
