package org.codetome.zircon.api

import org.codetome.zircon.Modifier
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import org.codetome.zircon.graphics.style.StyleSet

class TextCharacterBuilder {
    private var character: Char = ' '
    private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR
    private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR
    private var modifiersToUse: Set<Modifier> = setOf()

    fun character(character: Char) = also {
        this.character = character
    }

    fun styleSet(styleSet: StyleSet) = also {
        backgroundColor = styleSet.getBackgroundColor()
        foregroundColor = styleSet.getForegroundColor()
        modifiersToUse = styleSet.getActiveModifiers().toSet()
    }

    fun foregroundColor(foregroundColor: TextColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.backgroundColor = backgroundColor
    }

    fun modifier(vararg modifiers: Modifier) = also {
        modifiersToUse = modifiers.toSet()
    }

    fun build() = TextCharacter(
            character = character,
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiersToUse = modifiersToUse)

    companion object {

        @JvmStatic
        fun newBuilder() = TextCharacterBuilder()
    }
}