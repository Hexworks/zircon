package org.codetome.zircon.font

data class CharacterMetadata(val char: Char,
                             val tags: List<String> = listOf(),
                             val x: Int,
                             val y: Int)