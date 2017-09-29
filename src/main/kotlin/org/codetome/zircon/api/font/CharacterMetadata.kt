package org.codetome.zircon.api.font

/**
 * Metadata about a [Char], like `tags` and its position (x, y) in a [Font].
 */
data class CharacterMetadata(val char: Char,
                             val tags: Set<String> = setOf(),
                             val x: Int,
                             val y: Int)