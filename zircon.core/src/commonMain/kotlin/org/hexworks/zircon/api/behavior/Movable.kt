package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.jvm.JvmStatic

/**
 * A [Movable] is a [Boundable] object that can change its position.
 */
interface Movable : Boundable {

    override val position: Position
    val positionProperty: ObservableValue<Position>

    override val size: Size
    val sizeProperty: ObservableValue<Size>

    /**
     * Sets the position of this [Movable].
     * @return `true` if the operation was successful, `false` if not
     */
    fun moveTo(position: Position): Boolean

    companion object {

        @JvmStatic
        fun create(
            size: Size,
            position: Position = Position.ZERO
        ): Movable =
            DefaultMovable(Boundable.create(position, size).toProperty())
    }
}
