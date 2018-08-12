package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.GraphicTile

class DefaultGraphicTile(override val name: String,
                         override val tags: Set<String>) : GraphicTile {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.sorted().joinToString()}])"

    override fun generateCacheKey() = cacheKey

    override fun withName(name: String) = DefaultGraphicTile(
            name = name,
            tags = tags)

    override fun withTags(tags: Set<String>) = DefaultGraphicTile(
            name = name,
            tags = tags)

}
