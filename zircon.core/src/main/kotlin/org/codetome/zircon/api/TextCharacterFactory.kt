package org.codetome.zircon.api

import org.codetome.zircon.api.graphics.StyleSet

expect object TextCharacterFactory {

    fun create(character: Char, styleSet: StyleSet, tags: Set<String>): TextCharacter
}
