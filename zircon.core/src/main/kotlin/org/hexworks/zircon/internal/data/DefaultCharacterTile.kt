package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultCharacterTile(
        override val character: Char,
        private val style: StyleSet = StyleSet.defaultStyle())
    : Drawable, CharacterTile {

    private val cacheKey = "CharacterTile(c=$character,s=${style.generateCacheKey()})"

    override fun generateCacheKey() = cacheKey

    override fun styleSet() = style

}
