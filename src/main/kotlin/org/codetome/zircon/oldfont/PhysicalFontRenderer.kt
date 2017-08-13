package org.codetome.zircon.oldfont

import org.codetome.zircon.TextCharacter
import java.awt.Font

/**
 * This class wraps the built-in [java.awt.Font] class.
 */
class PhysicalFontRenderer<out I, in T>(private val fontsInOrderOfPriority: MutableList<Font>,
                                        private val characterImageRenderer: CharacterImageRenderer<I, T>,
                                        private val antiAliased: Boolean,
                                        width: Int,
                                        height: Int)
    : FontRenderer<T>(width, height) {


    override fun isAntiAliased() = antiAliased

    override fun renderCharacter(textCharacter: TextCharacter, surface: T, x: Int, y: Int) {
        characterImageRenderer.renderFromFont(
                textCharacter = textCharacter,
                font = getFontForCharacter(textCharacter),
                surface = surface,
                x = x,
                y = y)
    }


    private fun getFontForCharacter(character: TextCharacter): Font {
        var fontToUse = fontsInOrderOfPriority.firstOrNull { it.canDisplay(character.getCharacter()) }
                ?: throw IllegalArgumentException("Can't find a oldfont which can display character '${character.getCharacter()}'.")
        if (character.isBold()) {
            fontToUse = fontToUse.deriveFont(Font.BOLD)
        }
        if (character.isItalic()) {
            fontToUse = fontToUse.deriveFont(Font.ITALIC)
        }
        return fontToUse
    }
}