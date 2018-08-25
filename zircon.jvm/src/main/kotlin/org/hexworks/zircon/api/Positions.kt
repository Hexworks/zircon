package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.AbsolutePosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D

object Positions {

    /**
     * Constant for the top-left corner (0x0)
     */
    @JvmStatic
    fun topLeftCorner() = Position.topLeftCorner()

    /**
     * Constant for the 1x1 position (one offset in both directions from top-left)
     */
    @JvmStatic
    fun offset1x1() = Position.offset1x1()

    /**
     * This position can be considered as the default
     */
    @JvmStatic
    fun defaultPosition() = Position.defaultPosition()

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    @JvmStatic
    fun unknown() = Position.unknown()

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    @JvmStatic
    fun relativeToTopOf(component: Component) = Position.relativeToTopOf(component)

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    @JvmStatic
    fun relativeToRightOf(component: Component) = Position.relativeToRightOf(component)

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    @JvmStatic
    fun relativeToBottomOf(component: Component) = Position.relativeToBottomOf(component)

    /**
     * Creates a [Position] which is relative to the left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    @JvmStatic
    fun relativeToLeftOf(component: Component) = Position.relativeToLeftOf(component)

    /**
     * Factory method for creating a [Position].
     */
    @JvmStatic
    fun create(x: Int, y: Int) = Position.create(x, y)

    /**
     * Position3d(0, 0, 0)
     */
    @JvmStatic
    fun default3DPosition() = Position3D.defaultPosition()

    /**
     * Factory method for [Position3D].
     */
    @JvmStatic
    fun create3DPosition(x: Int, y: Int, z: Int) = Position3D.create(x = x, y = y, z = z)

    /**
     * Creates a new [Position3D] from a [Position].
     * If `y` is not supplied it defaults to `0` (ground level).
     */
    @JvmStatic
    @JvmOverloads
    fun from2DTo3D(position: Position, z: Int = 0) = create3DPosition(
            x = position.x,
            y = position.y,
            z = z)

    @JvmStatic
    fun createAbsolutePosition(x: Int, y: Int): Position {
        return AbsolutePosition(x, y)
    }

}
