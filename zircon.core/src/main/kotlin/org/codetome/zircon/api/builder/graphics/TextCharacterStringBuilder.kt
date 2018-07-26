package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.TextCharacterString
import org.codetome.zircon.api.graphics.TextWrap
import org.codetome.zircon.api.graphics.TextWrap.WRAP
import org.codetome.zircon.internal.graphics.DefaultTextCharacterString
import org.codetome.zircon.platform.factory.IdentifierFactory

/**
 * Creates [org.codetome.zircon.api.graphics.TextCharacterString]s.
 * Defaults:
 * - `text` is **mandatory**
 */
@Suppress("ArrayInDataClass")
data class TextCharacterStringBuilder(
        private var text: String = NO_VALUE,
        private var textWrap: TextWrap = WRAP,
        private val modifiers: MutableSet<Modifier> = mutableSetOf(),
        private var foregroundColor: TextColor = TextColor.defaultForegroundColor(),
        private var backgroundColor: TextColor = TextColor.defaultBackgroundColor())
    : Builder<TextCharacterString> {


    override fun build(): TextCharacterString {
        require(text != NO_VALUE) {
            "You must set some text for a TextCharacterString!"
        }
        require(text.isNotBlank()) {
            "'text' must not be blank!"
        }
        return DefaultTextCharacterString(
                textChars = text.map {
                    TileBuilder.newBuilder()
                            .foregroundColor(foregroundColor)
                            .backgroundColor(backgroundColor)
                            .character(it)
                            .modifiers(modifiers)
                            .build()
                },
                textWrap = textWrap)
    }

    override fun createCopy() = copy()

    fun text(text: String) = also {
        this.text = text
    }

    fun textWrap(textWrap: TextWrap) = also {
        this.textWrap = textWrap
    }

    fun modifiers(vararg modifier: Modifier) = also {
        this.modifiers.addAll(modifier.toSet())
    }

    fun foregroundColor(foregroundColor: TextColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.backgroundColor = backgroundColor
    }

    companion object {

        /**
         * Creates a new [TextCharacterStringBuilder] to build [org.codetome.zircon.api.graphics.TextCharacterString]s.
         */
        fun newBuilder() = TextCharacterStringBuilder()

        private val NO_VALUE = IdentifierFactory.randomIdentifier().toString()

    }
}
