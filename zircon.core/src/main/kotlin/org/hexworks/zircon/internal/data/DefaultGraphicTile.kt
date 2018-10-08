package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseGraphicTile

data class DefaultGraphicTile(override val name: String,
                              override val tags: Set<String>) : BaseGraphicTile() {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

}
