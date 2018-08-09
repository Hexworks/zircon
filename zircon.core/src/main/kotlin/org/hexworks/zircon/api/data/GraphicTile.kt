package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class GraphicTile(
        val name: String,
        val tags: Set<String>)
    : Tile {

    private val cacheKey = "GraphicTile(n=$name,t=[${tags.sorted().joinToString()}])"

    override fun tileType() = GraphicTile::class

    override fun generateCacheKey() = cacheKey

    override fun styleSet() = StyleSet.defaultStyle()

    fun withName(name: String) = GraphicTile(
            name = name,
            tags = tags)

    fun withTags(tags: Set<String>) = GraphicTile(
            name = name,
            tags = tags)

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
