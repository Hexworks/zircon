package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builds [CharacterTile]s.
 * Defaults:
 * - Default character is a space
 * - Default [StyleSet] comes from [StyleSet.defaultStyle]
 */
@ZirconDsl
class CharacterTileBuilder : Builder<CharacterTile> {

    var character: Char = ' '
    var styleSet: StyleSet = StyleSet.defaultStyle()

    override fun build(): CharacterTile {
        return DefaultCharacterTile(
            character = character,
            styleSet = styleSet,
        )
    }

    operator fun Char.unaryPlus() {
        character = this
    }
}

fun characterTile(init: CharacterTileBuilder.() -> Unit): CharacterTile {
    return CharacterTileBuilder().apply(init).build()
}

fun CharacterTileBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit) {
    this.styleSet = StyleSetBuilder().apply(init).build()
}
