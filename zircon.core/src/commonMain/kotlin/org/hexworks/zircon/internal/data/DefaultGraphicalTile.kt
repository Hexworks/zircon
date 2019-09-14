package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseGraphicalTile
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultGraphicalTile(override val name: String,
                                override val tags: Set<String>,
                                override val tileset: TilesetResource) : BaseGraphicalTile() {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

    override fun currentTileset(): TilesetResource {
        return tileset
    }

}
