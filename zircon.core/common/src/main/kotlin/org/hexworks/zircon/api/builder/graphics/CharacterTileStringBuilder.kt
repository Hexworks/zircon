package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WRAP
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultCharacterTileString

/**
 * Creates [org.hexworks.zircon.api.graphics.CharacterTileString]s.
 * Defaults:
 * - `text` is **mandatory**
 */
@Suppress("ArrayInDataClass")
data class CharacterTileStringBuilder(
        private var text: String = NO_VALUE,
        private var textWrap: TextWrap = WRAP,
        private val modifiers: MutableSet<Modifier> = mutableSetOf(),
        private var foregroundColor: TileColor = TileColor.defaultForegroundColor(),
        private var backgroundColor: TileColor = TileColor.defaultBackgroundColor())
    : Builder<CharacterTileString> {

    override fun createCopy() = copy()

    fun withText(text: String) = also {
        this.text = text
    }

    fun withTextWrap(textWrap: TextWrap) = also {
        this.textWrap = textWrap
    }

    fun withModifiers(vararg modifier: Modifier) = also {
        this.modifiers.addAll(modifier.toSet())
    }

    fun withForegroundColor(foregroundColor: TileColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun withBackgroundColor(backgroundColor: TileColor) = also {
        this.backgroundColor = backgroundColor
    }

    override fun build(): CharacterTileString {
        require(text != NO_VALUE) {
            "You must set some text for a CharacterTileString!"
        }
        require(text.isNotBlank()) {
            "'text' must not be blank!"
        }
        return DefaultCharacterTileString(
                textChars = text.map {
                    TileBuilder.newBuilder()
                            .withForegroundColor(foregroundColor)
                            .withBackgroundColor(backgroundColor)
                            .withCharacter(it)
                            .withModifiers(modifiers)
                            .buildCharacterTile()
                },
                textWrap = textWrap)
    }

    companion object {

        /**
         * Creates a new [CharacterTileStringBuilder] to build [org.hexworks.zircon.api.graphics.CharacterTileString]s.
         */
        fun newBuilder() = CharacterTileStringBuilder()

        private val NO_VALUE = IdentifierFactory.randomIdentifier().toString()

    }
}
