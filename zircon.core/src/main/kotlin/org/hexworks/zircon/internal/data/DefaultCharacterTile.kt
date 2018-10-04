package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultCharacterTile(
        override val character: Char,
        override val styleSet: StyleSet = StyleSet.defaultStyle())
    : Drawable, CharacterTile {

    override fun createCopy() = copy()

    override val foregroundColor: TileColor
        get() = styleSet.foregroundColor

    override val backgroundColor: TileColor
        get() = styleSet.backgroundColor

    override val modifiers: Set<Modifier>
        get() = styleSet.modifiers

    private val cacheKey = "CharacterTile(c=$character,s=${styleSet.generateCacheKey()})"

    override fun generateCacheKey() = cacheKey

}
