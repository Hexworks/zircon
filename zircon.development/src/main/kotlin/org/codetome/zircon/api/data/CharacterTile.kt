package org.codetome.zircon.api.data

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.modifier.Modifier

data class CharacterTile(override val key: Char,
                         private val style: StyleSet = StyleSet.defaultStyle())
    : Drawable<Char>, Tile<Char> {

    override fun toStyleSet() = style

    override fun withKey(key: Char) = Tile.create(key, style)

    override fun withForegroundColor(foregroundColor: TextColor): Tile<Char> {
        return Tile.create(key, style.withForegroundColor(foregroundColor))
    }

    override fun withBackgroundColor(backgroundColor: TextColor): Tile<Char> {
        return Tile.create(key, style.withBackgroundColor(backgroundColor))
    }

    override fun withStyle(styleSet: StyleSet): Tile<Char> {
        return Tile.create(key, styleSet)
    }

    override fun withModifiers(vararg modifiers: Modifier): Tile<Char> {
        return withModifiers(modifiers.toSet())
    }

    override fun withModifiers(modifiers: Set<Modifier>): Tile<Char> {
        return Tile.create(key, style.withModifiers(modifiers))
    }

    override fun withoutModifiers(vararg modifiers: Modifier): Tile<Char> {
        return withoutModifiers(modifiers.toSet())
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): Tile<Char> {
        return Tile.create(key, style.withRemovedModifiers(modifiers))
    }
}
