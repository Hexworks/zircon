package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.jvm.JvmStatic

/**
 * A [Movable] is a [Boundable] object which can change its position.
 */
interface Movable : Boundable {

    /**
     * Sets the position of this [Movable].
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

    companion object {

        @JvmStatic
        fun create(size: Size, position: Position = Position.zero()): Movable =
                DefaultMovable(size, position)
    }
}
