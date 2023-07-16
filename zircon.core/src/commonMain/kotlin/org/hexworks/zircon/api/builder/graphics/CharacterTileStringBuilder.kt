package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WRAP
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultCharacterTileString

private val NO_VALUE = UUID.randomUUID().toString()

/**
 * Creates [CharacterTileString]s.
 * Defaults:
 * - `text` is **mandatory**
 */
@ZirconDsl
class CharacterTileStringBuilder : Builder<CharacterTileString> {

    var text: String = NO_VALUE
        set(value) {
            field = value
            if (size.isUnknown) {
                size = Size.create(text.length, 1)
            }
        }

    var textWrap: TextWrap = WRAP
    var size: Size = Size.unknown()
    val modifiers: MutableSet<Modifier> = mutableSetOf()
    var styleSet: StyleSet = StyleSet.defaultStyle()

    override fun build(): CharacterTileString {
        val template = characterTile {
            styleSet = styleSet
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
fun characterTileString(init: CharacterTileStringBuilder.() -> Unit): CharacterTileString =
    CharacterTileStringBuilder().apply(init).build()
