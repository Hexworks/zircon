package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WRAP
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultCharacterTileString

/**
 * Creates [CharacterTileString]s.
 * Defaults:
 * - `text` is **mandatory**
 */
@Suppress("ArrayInDataClass")
data class CharacterTileStringBuilder(
        private var text: String = NO_VALUE,
        private var textWrap: TextWrap = WRAP,
        private var size: Size = Size.unknown(),
        private val modifiers: MutableSet<Modifier> = mutableSetOf(),
        private var foregroundColor: TileColor = TileColor.defaultForegroundColor(),
        private var backgroundColor: TileColor = TileColor.defaultBackgroundColor())
    : Builder<CharacterTileString> {

    override fun createCopy() = copy()

    fun withText(text: String) = also {
        this.text = text
        if (size.isUnknown) {
            size = Size.create(text.length, 1)
        }
    }

    fun withSize(size: Size) = also {
        this.size = size
    }

    fun withSize(width: Int, height: Int) = also {
        this.size = Size.create(width, height)
    }

    fun withTextWrap(textWrap: TextWrap) = also {
        this.textWrap = textWrap
    }

    fun withStyleSet(styleSet: StyleSet) = also {
        modifiers.clear()
        modifiers.addAll(styleSet.modifiers)
        foregroundColor = styleSet.foregroundColor
        backgroundColor = styleSet.backgroundColor
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
        return DefaultCharacterTileString(
                characterTiles = if (text == NO_VALUE || text.isBlank()) {
                    listOf()
                } else {
                    text.map {
                        TileBuilder.newBuilder()
                                .withForegroundColor(foregroundColor)
                                .withBackgroundColor(backgroundColor)
                                .withCharacter(it)
                                .withModifiers(modifiers)
                                .buildCharacterTile()
                    }
                },
                size = size,
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
