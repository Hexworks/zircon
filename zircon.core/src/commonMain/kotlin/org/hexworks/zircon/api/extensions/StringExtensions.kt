package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap

fun String.toCharacterTileString(
    styleSet: StyleSet = StyleSet.defaultStyle(),
    textWrap: TextWrap = TextWrap.WRAP,
    size: Size = Size.create(length, 1)
) = characterTileString {
    text = this@toCharacterTileString
    this.styleSet = styleSet
    this.textWrap = textWrap
    this.size = size
}

fun Char.toCharacterTile(styleSet: StyleSet = StyleSet.defaultStyle()) = characterTile {
    character = this@toCharacterTile
    this.styleSet = styleSet
}