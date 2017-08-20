package org.codetome.zircon.behavior

import org.codetome.zircon.Position

/**
 * A [Movable] object is a specialized [Positionable].
 * It can not only be positioned but moved as well.
 */
interface Movable : Positionable {

    /**
     * Sets the position of this [Movable] relative to its parent.
     */
    fun moveTo(position: Position)
}