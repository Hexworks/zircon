package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position

/**
 * Represents an object which is positionable within its parent.
 * For example a [org.hexworks.zircon.api.component.Component] can be positioned
 * relative to is parent or an [org.hexworks.zircon.api.graphics.Layer]
 * can be positioned within a [org.hexworks.zircon.api.grid.TileGrid].
 */
interface Movable {

    /**
     * Returns the position of this [Movable]. The position of a [Movable] is
     * its position relative to its parent. A position of (0, 0) denotes the top left
     * corner of the parent.
     */
    fun position(): Position

    /**
     * Sets the position of this [Movable].
     * Note that if the supplied `position` is same as the [Movable]'s current
     * position nothing will change.
     * @return `true` if the position was changed `false` otherwise
     */
    fun moveTo(position: Position): Boolean

    fun moveBy(position: Position) = moveTo(position() + position)

    fun moveRightBy(delta: Int) = moveTo(position().withRelativeX(delta))

    fun moveLeftBy(delta: Int) = moveTo(position().withRelativeX(-delta))

    fun moveUpBy(delta: Int) = moveTo(position().withRelativeY(-delta))

    fun moveDownBy(delta: Int) = moveTo(position().withRelativeY(delta))
}
