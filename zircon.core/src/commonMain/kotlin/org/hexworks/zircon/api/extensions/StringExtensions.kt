package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap

public fun String.toCharacterTileString(
    styleSet: StyleSet = StyleSet.defaultStyle(),
    textWrap: TextWrap = TextWrap.WRAP,
    size: Size = Size(length, 1)
): CharacterTileString = characterTileString {
    text = this@toCharacterTileString
    this.styleSet = styleSet
    this.textWrap = textWrap
    this.size = size
}

public fun Char.toCharacterTile(styleSet: StyleSet = StyleSet.defaultStyle()): CharacterTile = characterTile {
    character = this@toCharacterTile
    this.styleSet = styleSet
}