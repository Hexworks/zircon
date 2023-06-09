package org.hexworks.zircon.internal.data

import korlibs.image.tiles.TileSet
import org.hexworks.zircon.api.data.base.BaseCharacterTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultCharacterTile internal constructor(
    override val character: Char,
    override val styleSet: StyleSet = StyleSet.defaultStyle(),
    override val tileset: TilesetResource? = null
) : BaseCharacterTile() {

    override val cacheKey: String
        get() = "CharacterTile(c=$character,s=${styleSet.cacheKey})"

    override fun createCopy() = copy()

}
