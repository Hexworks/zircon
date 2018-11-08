package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseCharacterTile
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultCharacterTile(
        override val character: Char,
        override val styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseCharacterTile() {

    private val cacheKey = "CharacterTile(c=$character,s=${styleSet.generateCacheKey()})"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

}
