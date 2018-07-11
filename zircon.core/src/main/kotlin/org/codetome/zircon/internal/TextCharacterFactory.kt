package org.codetome.zircon.internal

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.StyleSet

expect object TextCharacterFactory {

    /**
     * Creates a new [TextCharacter].
     */
    fun create(character: Char,
               styleSet: StyleSet,
               tags: Set<String>): TextCharacter

}
