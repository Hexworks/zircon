@file:Suppress("unused", "ReplaceSingleLineLet", "SpellCheckingInspection", "RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition
import kotlin.jvm.JvmStatic

/**
 * Represents a coordinate on a 2D plane. [Position]
 * destructures into [x] and [y].
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Position : Comparable<Position> {

    val x: Int
    val y: Int

    /**
     * Tells whether this [Position] is `UNKNOWN`.
     */
    val isUnknown: Boolean

    /**
     * Tells whether this [Position] is not `UNKNOWN`.
     */
    val isNotUnknown: Boolean

    /**
     * Tells whether this [Position] has a negative component (x or y) or not.
     */
    val hasNegativeComponent: Boolean

    operator fun component1() = x

    operator fun component2() = y

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    operator fun plus(other: Position): Position

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    operator fun minus(other: Position): Position

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied x index.
     */
    fun withX(x: Int): Position

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied y index.
     */
    fun withY(y: Int): Position

    /**
     * Creates a new [Position] object representing a position on the same y, but with a x offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive
     * delta will returnThis a grid position <code>delta</code> number of x to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeX(delta: Int): Position

    /**
     * Creates a new [Position] object representing a position on the same x, but with a y offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive delta
     * will returnThis a grid position <code>delta</code> number of y to the down and for negative
     * numbers the same up.
     */
    fun withRelativeY(delta: Int): Position

    /**
     * Creates a new [Position] object that is translated by an amount of x and y specified by another
     * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
     */
    fun withRelative(translate: Position): Position

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    fun relativeToTopOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToRightOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToBottomOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the top left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    fun relativeToLeftOf(component: Component): Position

    /**
     * Transforms this [Position] to a [Size] so if
     * this position is Position(x=2, y=3) it will become
     * Size(x=2, y=3).
     */
    fun toSize(): Size

    /**
     * Turns this [Position] to a [PixelPosition]. Has no effect if
     * `this` object is a [PixelPosition].
     */
    @Beta
    fun toPixelPosition(tileset: TilesetResource): PixelPosition

    /**
     * Creates a new [Position3D] from the [x] and [y] components of this [Position]
     * and the given [z] value.
     */
    fun toPosition3D(z: Int): Position3D

    companion object {

        /**
         * Constant for the top-left corner (0x0)
         */
        @JvmStatic
        fun topLeftCorner() = TOP_LEFT_CORNER

        /**
         * Constant for the 1x1 position (one offset in both directions from top-left)
         */
        @JvmStatic
        fun offset1x1() = OFFSET_1X1

        /**
         * Constant for the 0x0 position.
         */
        @JvmStatic
        fun zero() = DEFAULT_POSITION

        /**
         * This position can be considered as the default (0x0).
         */
        @JvmStatic
        fun defaultPosition() = DEFAULT_POSITION

        /**
         * Used in place of a possible null value. Means that the position is unknown (cursor for example).
         */
        @JvmStatic
        fun unknown() = UNKNOWN

        /**
         * Creates a new [Position] using the given `x` and `y` values.
         */
        @JvmStatic
        fun create(x: Int, y: Int): Position {
            return GridPosition(x, y)
        }

        /**
         * Returns the top left position of the given [Component].
         */
        @JvmStatic
        fun topLeftOf(component: Component) = component.position

        /**
         * Returns the top right position of the given [Component].
         */
        @JvmStatic
        fun topRightOf(component: Component) = component.position.withRelativeX(component.width)

        /**
         * Returns the bottom left position of the given [Component].
         */
        @JvmStatic
        fun bottomLeftOf(component: Component) = component.position.withRelativeY(component.height)

        /**
         * Returns the bottom right position of the given [Component].
         */
        @JvmStatic
        fun bottomRightOf(component: Component) = component.position.withRelative(component.size.toPosition())

        private val TOP_LEFT_CORNER = create(0, 0)
        private val OFFSET_1X1 = create(1, 1)
        private val DEFAULT_POSITION = TOP_LEFT_CORNER
        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
