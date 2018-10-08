package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.api.util.Maybe

/**
 * Base class for all [Tile] implementations.
 */
abstract class BaseTile : Tile {

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.setTileAt(position, this)
    }

    override fun asCharacterTile() = Maybe.ofNullable(this as? CharacterTile)

    override fun asImageTile() = Maybe.ofNullable(this as? ImageTile)

    override fun asGraphicTile() = Maybe.ofNullable(this as? GraphicTile)

    override fun isOpaque(): Boolean = foregroundColor.isOpaque().and(
            backgroundColor.isOpaque())

    override fun isUnderlined(): Boolean = modifiers.contains(Underline)

    override fun isCrossedOut(): Boolean = modifiers.contains(CrossedOut)

    override fun isBlinking(): Boolean = modifiers.contains(Blink)

    override fun isVerticalFlipped(): Boolean = modifiers.contains(VerticalFlip)

    override fun isHorizontalFlipped(): Boolean = modifiers.contains(HorizontalFlip)

    override fun hasBorder(): Boolean = modifiers.any { it is Border }

    override fun fetchBorderData(): Set<Border> = modifiers
            .asSequence()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    override fun isEmpty(): Boolean = this === Tile.empty()

    override fun isNotEmpty(): Boolean = this !== Tile.empty()


}
