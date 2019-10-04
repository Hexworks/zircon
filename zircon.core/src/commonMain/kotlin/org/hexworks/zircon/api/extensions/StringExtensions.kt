package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.StyleSets
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap

fun String.toCharacterTileString(styleSet: StyleSet = StyleSets.defaultStyle(),
                                 textWrap: TextWrap = TextWrap.WRAP,
                                 size: Size = Sizes.create(length, 1)): CharacterTileString {
    return CharacterTileStrings.newBuilder()
            .withText(this)
            .withStyleSet(styleSet)
            .withTextWrap(textWrap)
            .withSize(size)
            .build()
}

fun Char.toCharacterTile(styleSet: StyleSet = StyleSets.defaultStyle()): CharacterTile {
    return Tiles.newBuilder()
            .withCharacter(this)
            .withStyleSet(styleSet)
            .buildCharacterTile()
}
