package org.codetome.zircon.api.data

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.tileset.Tileset

data class ImageTile<S : Any>(override val key: String,
                              private var tileset: Tileset<String, S>,
                              private val style: StyleSet = StyleSet.defaultStyle())
    : Tile<String>, TilesetOverride<String, S> {

    override fun toStyleSet() = style

    override fun withKey(key: String) = ImageTile(
            key = key,
            tileset = tileset,
            style = style)

    override fun withForegroundColor(foregroundColor: TextColor): Tile<String> {
        return ImageTile(
                key = key,
                tileset = tileset,
                style = style.withForegroundColor(foregroundColor))
    }

    override fun withBackgroundColor(backgroundColor: TextColor): Tile<String> {
        return ImageTile(
                key = key,
                tileset = tileset,
                style = style.withBackgroundColor(backgroundColor))
    }

    override fun withStyle(styleSet: StyleSet): Tile<String> {
        return ImageTile(
                key = key,
                tileset = tileset,
                style = styleSet)
    }

    override fun withModifiers(vararg modifiers: Modifier): Tile<String> {
        return withModifiers(modifiers.toSet())
    }

    override fun withModifiers(modifiers: Set<Modifier>): Tile<String> {
        return ImageTile(
                key = key,
                tileset = tileset,
                style = style.withModifiers(modifiers))
    }

    override fun withoutModifiers(vararg modifiers: Modifier): Tile<String> {
        return withoutModifiers(modifiers.toSet())
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): Tile<String> {
        return ImageTile(
                key = key,
                tileset = tileset,
                style = style.withRemovedModifiers(modifiers))
    }

    override fun tileset() = tileset

    override fun useTileset(tileset: Tileset<String, S>) {
        this.tileset = tileset
    }
}
