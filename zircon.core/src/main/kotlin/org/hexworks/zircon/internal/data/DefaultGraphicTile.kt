package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.GraphicTile

data class DefaultGraphicTile(override val name: String,
                              override val tags: Set<String>) : GraphicTile {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

    override fun withName(name: String) = DefaultGraphicTile(
            name = name,
            tags = tags)

    override fun withTags(tags: Set<String>) = DefaultGraphicTile(
            name = name,
            tags = tags)

}
