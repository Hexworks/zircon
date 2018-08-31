package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.api.util.Maybe

abstract class BaseTile : Tile {

    /**
     * Returns this [BaseTile] as a [CharacterTile] if possible.
     */
    override fun asCharacterTile() = Maybe.ofNullable(this as? CharacterTile)

    /**
     * Returns this [BaseTile] as an [ImageTile] if possible.
     */
    override fun asImageTile() = Maybe.ofNullable(this as? ImageTile)

    /**
     * Returns this [BaseTile] as a [GraphicTile] if possible.
     */
    override fun asGraphicTile() = Maybe.ofNullable(this as? GraphicTile)

    override fun isOpaque(): Boolean = getForegroundColor().isOpaque().and(
            getBackgroundColor().isOpaque())

    override fun getForegroundColor(): TileColor = styleSet().foregroundColor()

    override fun getBackgroundColor(): TileColor = styleSet().backgroundColor()

    override fun getModifiers(): Set<Modifier> = styleSet().modifiers()

    override fun isUnderlined(): Boolean = getModifiers().contains(Underline)

    override fun isCrossedOut(): Boolean = getModifiers().contains(CrossedOut)

    override fun isBlinking(): Boolean = getModifiers().contains(Blink)

    override fun isVerticalFlipped(): Boolean = getModifiers().contains(VerticalFlip)

    override fun isHorizontalFlipped(): Boolean = getModifiers().contains(HorizontalFlip)

    override fun hasBorder(): Boolean = getModifiers().any { it is Border }

    override fun fetchBorderData(): Set<Border> = getModifiers()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    override fun isNotEmpty(): Boolean = this !== Tile.empty()

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.setTileAt(position, this)
    }

    /**
     * Returns a copy of this [BaseTile] with the specified modifiers.
     */
    override fun withModifiers(vararg modifiers: Modifier): Tile = withModifiers(modifiers.toSet())


    /**
     * Returns a copy of this [BaseTile] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [BaseTile] doesn't have the [Modifier] (s) specified, it will return itself.
     */
    override fun withoutModifiers(vararg modifiers: Modifier): Tile = withoutModifiers(modifiers.toSet())

}
