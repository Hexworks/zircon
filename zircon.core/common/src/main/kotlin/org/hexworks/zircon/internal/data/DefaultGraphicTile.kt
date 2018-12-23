package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseGraphicTile
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultGraphicTile(override val name: String,
                              override val tags: Set<String>,
                              override val tileset: TilesetResource) : BaseGraphicTile() {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

    override fun currentTileset(): TilesetResource {
        return tileset
    }

}
