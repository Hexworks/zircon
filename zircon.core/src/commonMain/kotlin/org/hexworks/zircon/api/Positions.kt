package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.impl.Position3D
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

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
     * Constant for the (0, 0) position
     */
    @JvmStatic
    fun zero() = Position.zero()

    /**
     * This position can be considered as the default (0, 0)
     */
    @JvmStatic
    fun defaultPosition() = Position.defaultPosition()

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    @JvmStatic
    fun unknown() = Position.unknown()

    /**
     * Returns the top left position of the given [Component].
     */
    @JvmStatic
    fun topLeftOf(component: Component) = Position.topLeftOf(component)

    /**
     * Returns the top right position of the given [Component].
     */
    @JvmStatic
    fun topRightOf(component: Component) = Position.topRightOf(component)

    /**
     * Returns the bottom left position of the given [Component].
     */
    @JvmStatic
    fun bottomLeftOf(component: Component) = Position.bottomLeftOf(component)

    /**
     * Returns the bottom right position of the given [Component].
     */
    @JvmStatic
    fun bottomRightOf(component: Component) = Position.bottomRightOf(component)

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

}
