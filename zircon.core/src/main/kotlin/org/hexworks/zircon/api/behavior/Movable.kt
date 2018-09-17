package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position

/**
 * A [Movable] object is a specialized [Positionable].
 * It can not only be positioned but moved as well.
 */
interface Movable : Positionable {

    /**
     * Sets the position of this [Movable].
     * Note that if the supplied `position` is same as the [Movable]'s current
     * position nothing will change.
     * @returnThis `true` if the position was changed `false` otherwise
     */
    fun moveTo(position: Position): Boolean

    fun moveBy(position: Position) = moveTo(position() + position)

    fun moveRightBy(delta: Int) = moveTo(position().withRelativeX(delta))

    fun moveLeftBy(delta: Int) = moveTo(position().withRelativeX(-delta))

    fun moveUpBy(delta: Int) = moveTo(position().withRelativeY(-delta))

    fun moveDownBy(delta: Int) = moveTo(position().withRelativeY(delta))
}
