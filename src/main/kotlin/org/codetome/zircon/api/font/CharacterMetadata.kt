package org.codetome.zircon.api.font

/**
 * Metadata about a [Char], like `tags` and position (x, y).
 */
data class CharacterMetadata(val char: Char,
                             val tags: Set<String> = setOf(),
                             val x: Int,
                             val y: Int)