package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.isSizeUnknown
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WRAP
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultCharacterTileString

private val NO_VALUE = UUID.randomUUID().toString()

/**
 * Creates [CharacterTileString]s.
 * Defaults:
 * - `text` is **mandatory**
 */
@ZirconDsl
public class CharacterTileStringBuilder : Builder<CharacterTileString> {

    public var text: String = NO_VALUE
        set(value) {
            field = value
            if (size.isSizeUnknown) {
                size = Size(text.length, 1)
            }
        }

    public var textWrap: TextWrap = WRAP
    public var size: Size = Size.UNKNOWN
    public var styleSet: StyleSet = StyleSet.defaultStyle()
    public val modifiers: MutableSet<Modifier> = mutableSetOf()

    public operator fun String.unaryPlus() {
        text = this
    }

    override fun build(): CharacterTileString {
        val styleSet = this.styleSet
        val template = characterTile {
            this.styleSet = styleSet
            character = ' '
        }
        return DefaultCharacterTileString(
            characterTiles = if (text == NO_VALUE || text.isBlank()) {
                listOf()
            } else {
                text.map(template::withCharacter)
            },
            size = size,
            textWrap = textWrap
        )
    }
}

/**
 * Creates a new [CharacterTileStringBuilder] using the builder DSL and returns it.
 */
public fun characterTileString(init: CharacterTileStringBuilder.() -> Unit): CharacterTileString =
    CharacterTileStringBuilder().apply(init).build()


public fun CharacterTileStringBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit): CharacterTileStringBuilder = apply {
    styleSet = StyleSetBuilder().apply(init).build()
}
