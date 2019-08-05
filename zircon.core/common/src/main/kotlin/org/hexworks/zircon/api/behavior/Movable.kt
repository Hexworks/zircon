package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid

/**
 * Represents an object which is positionable within its parent.
 * For example a [Component] can be positioned relative to is parent
 * or an [Layer] can be positioned within a [TileGrid].
 */
interface Movable : Boundable {

    /**
     * Sets the position of this [Movable].
     * Note that if the supplied `position` is same as the [Movable]'s current
     * position nothing will change.
     */
    fun moveTo(position: Position)

    /**
     * Moves this [Movable] relative to its current position by the given
     * [position]. Eg.: if its current position is (3, 2) and it is moved by
     * (-1, 2), its new position will be (2, 4).
     */
    fun moveBy(position: Position) = moveTo(this.position + position)

    fun moveRightBy(delta: Int) = moveTo(position.withRelativeX(delta))

    fun moveLeftBy(delta: Int) = moveTo(position.withRelativeX(-delta))

    fun moveUpBy(delta: Int) = moveTo(position.withRelativeY(-delta))

    fun moveDownBy(delta: Int) = moveTo(position.withRelativeY(delta))
}
