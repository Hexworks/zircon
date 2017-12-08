package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.Position

/**
 * Represents a coordinate in 3D space. Extends [Position] with
 * a `height` dimension. Use [Position3D.from2DPosition] and [Position3D.to2DPosition]
 * to convert between the two.
 * **Important:**
 * - `width: 0` is considered the **leftmost** position in a 3D space
 * - `depth: 0` is considered the **closest** position in a 3D space
 * - `height: 0` is considered **bottommost** position in a 3D space
 */
@Beta
data class Position3D(private val position: Position,
                      val z: Int) : Comparable<Position3D> {

    val x get() = position.column

    val y get() = position.row

    init {
        require(z >= 0) {
            "A position must have a height which is greater than or equal to 0!"
        }
    }

    /**
     * Returns a new [Position3D] which is the sum of `width`, `depth` and height in both [Position3D]s.
     * so `Position3D(width = 1, depth = 1, height = 1).minus(Position3D(width = 2, depth = 2, height = 2))` will be
     * `Position3D(width = 3, depth = 3, height = 3)`.
     */
    operator fun plus(position: Position3D) = Position3D(
            position = this.position + position.position,
            z = z + position.z)

    /**
     * Returns a new [Position3D] which is the difference between `width`, `depth` and height in both [Position3D]s.
     * so `Position3D(width = 3, depth = 3, height = 3).minus(Position3D(width = 2, depth = 2, height = 2))` will be
     * `Position3D(width = 1, depth = 1, height = 1)`.
     */
    operator fun minus(position: Position3D) = Position3D(
            position = this.position - position.position,
            z = z - position.z)

    /**
     * Creates a new [Position3D] object representing a 3D position with the same width and height as this but with
     * the supplied depth.
     */
    fun withY(y: Int) =
            if (y == position.row) {
                this
            } else {
                copy(position = position.withRow(y), z = z)
            }

    /**
     * Creates a new [Position3D] object representing a position with the same depth and height as this but with
     * the supplied width.
     */
    fun withX(x: Int) =
            if (x == position.column) {
                this
            } else {
                copy(position = position.withColumn(x), z = z)
            }

    /**
     * Creates a new [Position3D] object representing a position with the same depth and width as this but with
     * the supplied height.
     */
    fun withZ(z: Int) =
            if (z == this.z) {
                this
            } else {
                copy(position = position, z = z)
            }

    /**
     * Creates a new [Position3D] object representing a position on the same depth and height,
     * but with a width offset by the supplied value.
     * Calling this method with deltaX 0 will return `this`, calling it with a positive
     * deltaX will return a position <code>deltaX</code> number of width to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeX(deltaX: Int) =
            if (deltaX == 0) {
                this
            } else {
                withX(position.column + deltaX)
            }

    /**
     * Creates a new [Position3D] object representing a position on the same width and height,
     * but with a depth offset by the supplied value.
     * Calling this method with deltaY 0 will return `this`, calling it with a positive
     * deltaY will return a position <code>deltaY</code> number of width to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeY(deltaY: Int) =
            if (deltaY == 0) {
                this
            } else {
                withY(position.row + deltaY)
            }

    /**
     * Creates a new [Position3D] object representing a position on the same depth and width,
     * but with a height offset by the supplied value.
     * Calling this method with deltaZ 0 will return `this`, calling it with a positive
     * deltaZ will return a position <code>deltaZ</code> number of width to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeZ(deltaZ: Int) =
            if (deltaZ == 0) {
                this
            } else {
                withZ(z + deltaZ)
            }

    /**
     * Creates a new [Position3D] object that is translated by an amount of depth and width specified by another
     * [Position3D]. Same as calling
     * `withRelativeY(translate.getDepth()).withRelativeX(translate.getWidth()).withRelativeX(translate.getWidth())`.
     */
    fun withRelative(translate: Position3D) = withRelativeY(translate.position.row)
            .withRelativeX(translate.position.column)
            .withRelativeZ(translate.z)


    /**
     * Transforms this [Position3D] to a [Size3D] so if
     * this position is Position(width=2, depth=3, height=1) it will become
     * Size(width=2, depth=3, height=1).
     */
    fun toSize() = Size3D.of(
            columns = position.column,
            rows = position.row,
            levels = z)

    /**
     * Transforms this [Position3D] to a [Position]. Note that
     * the `height` component is lost during the conversion!
     */
    fun to2DPosition() = position

    override fun compareTo(other: Position3D): Int {
        return when {
            z < other.z -> -1
            z == other.z -> position.compareTo(other.position)
            else -> 1
        }
    }

    companion object {

        /**
         * Factory method for [Position3D].
         */
        @JvmStatic
        fun of(x: Int, y: Int, z: Int) = Position3D(Position(
                column = x,
                row = y),
                z = z)

        /**
         * Creates a new [Position3D] from a [Position].
         * If `height` is not supplied it defaults to `0` (ground height).
         */
        @JvmOverloads
        @JvmStatic
        fun from2DPosition(position: Position, level: Int = 0) = Position3D(
                position = position,
                z = level)
    }
}