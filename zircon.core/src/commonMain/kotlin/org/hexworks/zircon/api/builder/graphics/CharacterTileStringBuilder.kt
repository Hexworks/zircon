package org.hexworks.zircon.api.builder.graphics

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.SizeBuilder
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
    var styleSet: StyleSet = StyleSet.defaultStyle()
    val modifiers: MutableSet<Modifier> = mutableSetOf()

    operator fun String.unaryPlus() {
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
fun characterTileString(init: CharacterTileStringBuilder.() -> Unit): CharacterTileString =
    CharacterTileStringBuilder().apply(init).build()

fun CharacterTileStringBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}

fun CharacterTileStringBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit) = apply {
    styleSet = StyleSetBuilder().apply(init).build()
}
