package org.codetome.zircon.font

/**
 * Metadata about a [Char], like `tags` and position (x, y).
 */
data class CharacterMetadata(val char: Char,
                             val tags: List<String> = listOf(),
                             val x: Int,
                             val y: Int)