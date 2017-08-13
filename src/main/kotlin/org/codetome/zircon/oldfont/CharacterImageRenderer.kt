package org.codetome.zircon.oldfont

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import java.awt.Font

interface CharacterImageRenderer<in I, in T> {

    /**
     * Renders a `textCharacter` on a given `surface` from a `oldfont`.
     */
    fun renderFromFont(textCharacter: TextCharacter,
                       font: Font,
                       surface: T,
                       x: Int,
                       y: Int)

    /**
     * Renders a text character from the given `image` to the given `surface`.
     */
    fun renderFromImage(foregroundColor: TextColor,
                        backgroundColor: TextColor,
                        image: I,
                        surface: T,
                        x: Int,
                        y: Int)
}