package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * A [Movable] object is a specialized [Positionable].
 * It can not only be positioned but moved as well.
 */
interface Movable : Positionable {

    /**
     * Sets the (absolute) position of this [Movable].
     * Note that if the supplied `position` is same as the [Movable]'s current
     * position nothing will change.
     * @return true if the position was changed false otherwise
     */
    fun moveTo(position: Position): Boolean
}