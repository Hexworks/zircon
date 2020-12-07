package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.jvm.JvmStatic

/**
 * A [Movable] is a [Boundable] object which can change its position.
 */
interface Movable : Boundable {

    /**
     * Observable value that can be used to observe the changes of [rect].
     */
    val rectValue: ObservableValue<Rect>

    /**
     * Sets the position of this [Movable].
     * @return `true` if the operation was successful, `false` if not
     */
    fun moveTo(position: Position): Boolean

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

        @Suppress("JVM_STATIC_IN_INTERFACE_1_6")
        @JvmStatic
        fun create(size: Size, position: Position = Position.zero()): Movable =
            DefaultMovable(size, position)
    }
}
