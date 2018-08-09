package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

data class ImageTile(
        val tileset: TilesetResource,
        val name: String,
        val tags: Set<String>,
        private val style: StyleSet = StyleSet.defaultStyle(),
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(tileset))
    : Tile,
        TilesetOverride by tilesetOverride {

    private val cacheKey = "ImageTile(t=${tileset.path},n=$name,t=[${tags.sorted().joinToString()}]," +
            "s=${style.generateCacheKey()})"

    override fun tileType() = ImageTile::class

    override fun generateCacheKey() = cacheKey

    override fun styleSet() = style

    fun withName(name: String) = ImageTile(
            name = name,
            tags = tags,
            tileset = tileset,
            style = style)

    fun withTags(tags: Set<String>) = ImageTile(
            name = name,
            tags = tags,
            tileset = tileset,
            style = style)

    override fun withForegroundColor(foregroundColor: TileColor): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withForegroundColor(foregroundColor))
    }

    override fun withBackgroundColor(backgroundColor: TileColor): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withBackgroundColor(backgroundColor))
    }

    override fun withStyle(style: StyleSet): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style)
    }

    override fun withModifiers(modifiers: Set<Modifier>): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withModifiers(modifiers))
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withRemovedModifiers(modifiers))
    }

}
