package org.codetome.zircon.internal

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.StyleSet

actual object TextCharacterFactory {
    /**
     * Creates a new [TextCharacter].
     */
    actual fun create(character: Char,
                      styleSet: StyleSet,
                      tags: Set<String>): TextCharacter = DefaultTextCharacter(character, styleSet, tags)

}
