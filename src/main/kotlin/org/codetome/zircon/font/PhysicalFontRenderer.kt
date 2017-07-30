package org.codetome.zircon.font

import org.codetome.zircon.Symbols
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.config.BoldMode
import java.awt.Font
import java.lang.reflect.Modifier.FINAL
import java.lang.reflect.Modifier.STATIC

/**
 * This class wraps the built-in [java.awt.Font] class.
 */
class PhysicalFontRenderer<out I, in T>(val fontsInOrderOfPriority: MutableList<Font>,
                                        private val boldMode: BoldMode,
                                        private val characterImageRenderer: CharacterImageRenderer<I, T>,
                                        width: Int,
                                        height: Int)
    : MonospaceFontRenderer<T>(width, height) {


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
                ?: throw IllegalArgumentException("Can't find a font which can display character '${character.getCharacter()}'.")
        if (boldMode == BoldMode.EVERYTHING
                || boldMode == BoldMode.EVERYTHING_BUT_SYMBOLS
                && isNotASymbol(character.getCharacter())) {
            if (character.isBold()) {
                fontToUse = fontToUse.deriveFont(Font.BOLD)
            }
        }
        if (character.isItalic()) {
            fontToUse = fontToUse.deriveFont(Font.ITALIC)
        }
        return fontToUse
    }

    private fun isNotASymbol(character: Char): Boolean {
        return !SYMBOLS_CACHE.contains(character)
    }

    companion object {
        private val SYMBOLS_CACHE = Symbols::class.java.fields
                .filter {
                    it.type == Char::class.javaPrimitiveType
                            && it.modifiers and FINAL != 0
                            && it.modifiers and STATIC != 0
                }
                .map {
                    try {
                        it.getChar(null)
                    } catch (ignore: Exception) {
                        throw RuntimeException("This should never have happened!")
                    }
                }.toSet()
    }
}