package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap

fun String.toCharacterTileString(
    styleSet: StyleSet = StyleSet.defaultStyle(),
    textWrap: TextWrap = TextWrap.WRAP,
    size: Size = Size.create(length, 1)
): CharacterTileString {
    return CharacterTileStrings.newBuilder()
        .withText(this)
        .withStyleSet(styleSet)
        .withTextWrap(textWrap)
        .withSize(size)
        .build()
}

fun Char.toCharacterTile(styleSet: StyleSet = StyleSet.defaultStyle()): CharacterTile {
    return Tile.newBuilder()
        .withCharacter(this)
        .withStyleSet(styleSet)
        .buildCharacterTile()
}
