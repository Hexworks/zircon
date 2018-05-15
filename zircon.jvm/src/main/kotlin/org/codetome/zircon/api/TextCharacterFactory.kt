package org.codetome.zircon.api

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.DefaultTextCharacter

actual object TextCharacterFactory {
    actual fun create(character: Char, styleSet: StyleSet, tags: Set<String>): TextCharacter {
        return DefaultTextCharacter(character, styleSet, tags)
    }
}
