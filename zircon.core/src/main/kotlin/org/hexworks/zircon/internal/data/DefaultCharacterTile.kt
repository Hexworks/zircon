package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultCharacterTile(
        override val character: Char,
        override val styleSet: StyleSet = StyleSet.defaultStyle())
    : Drawable, CharacterTile {

    private val cacheKey = "CharacterTile(c=$character,s=${styleSet.generateCacheKey()})"

    override fun generateCacheKey() = cacheKey

}
