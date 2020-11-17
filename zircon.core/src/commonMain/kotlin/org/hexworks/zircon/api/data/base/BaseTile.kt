package org.hexworks.zircon.api.data.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.api.modifier.SimpleModifiers.CrossedOut
import org.hexworks.zircon.api.modifier.SimpleModifiers.HorizontalFlip
import org.hexworks.zircon.api.modifier.SimpleModifiers.Underline
import org.hexworks.zircon.api.modifier.SimpleModifiers.VerticalFlip

/**
 * Base class for all [Tile] implementations.
 */
abstract class BaseTile : Tile {

    override val isOpaque: Boolean
        get() = backgroundColor.isOpaque

    override val isUnderlined: Boolean
        get() = modifiers.contains(Underline)

    override val isCrossedOut: Boolean
        get() = modifiers.contains(CrossedOut)

    override val isBlinking: Boolean
        get() = modifiers.contains(Blink)

    override val isVerticalFlipped: Boolean
        get() = modifiers.contains(VerticalFlip)

    override val isHorizontalFlipped: Boolean
        get() = modifiers.contains(HorizontalFlip)

    override val hasBorder: Boolean
        get() = modifiers.any { it is Border }

    override val isEmpty: Boolean
        get() = this === Tile.empty()

    override val isNotEmpty: Boolean
        get() = this != Tile.empty()

    override fun fetchBorderData(): Set<Border> = modifiers
            .asSequence()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    override fun asCharacterTile() = Maybe.ofNullable(this as? CharacterTile)

    override fun asImageTile() = Maybe.ofNullable(this as? ImageTile)

    override fun asGraphicTile() = Maybe.ofNullable(this as? GraphicalTile)

}
