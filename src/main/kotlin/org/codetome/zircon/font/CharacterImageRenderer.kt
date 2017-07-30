package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter
import java.awt.Font

interface CharacterImageRenderer<in I, in T> {

    /**
     * Renders a `textCharacter` on a given `surface` from a `font`.
     */
    fun renderFromFont(textCharacter: TextCharacter, font: Font, surface: T, x: Int, y: Int)

    /**
     * Renders a text character from the given `image` to the given `surface`.
     */
    fun renderFromImage(image: I, surface: T, x: Int, y: Int)
}