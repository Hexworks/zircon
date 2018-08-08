package org.codetome.zircon.api.data

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Modifier

data class GraphicTile(
        val name: String,
        val tags: Set<String>)
    : Tile {

    override fun tileType() = GraphicTile::class

    override fun generateCacheKey(): String {
        return "GraphicTile,name:$name,tags:${tags.sorted()}"
    }

    override fun toStyleSet() = throw UnsupportedOperationException("No.")

    fun withName(name: String) = GraphicTile(
            name = name,
            tags = tags)

    fun withTags(tags: Set<String>) = GraphicTile(
            name = name,
            tags = tags)

    override fun withForegroundColor(foregroundColor: TextColor) = this

    override fun withBackgroundColor(backgroundColor: TextColor) = this

    override fun withStyle(styleSet: StyleSet) = this

    override fun withModifiers(vararg modifiers: Modifier) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(vararg modifiers: Modifier) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
