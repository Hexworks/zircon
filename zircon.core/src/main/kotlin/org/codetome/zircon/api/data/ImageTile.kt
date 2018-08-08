package org.codetome.zircon.api.data

import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.behavior.impl.DefaultTilesetOverride

data class ImageTile(
        val tileset: TilesetResource<ImageTile>,
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

    override fun toStyleSet() = style

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

    override fun withForegroundColor(foregroundColor: TextColor): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withForegroundColor(foregroundColor))
    }

    override fun withBackgroundColor(backgroundColor: TextColor): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withBackgroundColor(backgroundColor))
    }

    override fun withStyle(styleSet: StyleSet): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = styleSet)
    }

    override fun withModifiers(vararg modifiers: Modifier): Tile {
        return withModifiers(modifiers.toSet())
    }

    override fun withModifiers(modifiers: Set<Modifier>): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withModifiers(modifiers))
    }

    override fun withoutModifiers(vararg modifiers: Modifier): Tile {
        return withoutModifiers(modifiers.toSet())
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): Tile {
        return ImageTile(
                name = name,
                tags = tags,
                tileset = tileset,
                style = style.withRemovedModifiers(modifiers))
    }

}
