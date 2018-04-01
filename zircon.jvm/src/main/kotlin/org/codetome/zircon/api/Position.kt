package org.codetome.zircon.api

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.internal.util.DefaultCache

/**
 * A 2D position in terminal space. Please note that the coordinates are 0-indexed, meaning 0x0 is the
 * top left corner of the terminal. This object is immutable so you cannot change it after it has been created.
 * Instead, you can easily create modified clones by using the `with*` methods.
 *
 * **Note that** the `copy` operation is not supported for this class. Use the `of` method to create a new
 * instance of [Position]!
 */
@Suppress("DataClassPrivateConstructor")
data class Position private constructor(
        /**
         * Represents the `x` in a terminal.
         */
        val x: Int,
        /**
         * Represents the `y` in a terminal
         */
        val y: Int) : Comparable<Position>, Cacheable   {

    init {
        require(x >= 0 && y >= 0) {
            "A position must have a x and a y which is greater than or equal to 0!"
        }
    }

    override fun generateCacheKey() = generateCacheKey(x, y)

    override fun compareTo(other: Position): Int = when {
        y > other.y -> 1
        y == other.y && x > other.x -> 1
        y == other.y && x == other.x -> 0
        else -> -1
    }

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    operator fun plus(position: Position): Position {
        return Position(
                x = x + position.x,
                y = y + position.y)
    }

    /**
     * Returns a new [Position] which is the difference between `x` and `y` in both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    operator fun minus(position: Position): Position {
        return Position(
                x = x - position.x,
                y = y - position.y)
    }

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied y index.
     */
    fun withY(y: Int): Position {
        if (y == 0 && this.x == 0) {
            return TOP_LEFT_CORNER
        }
        return copy(y = y)
    }

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied x index.
     */
    fun withX(x: Int): Position {
        if (x == 0 && this.y == 0) {
            return TOP_LEFT_CORNER
        }
        return copy(x = x)
    }

    /**
     * Creates a new [Position] object representing a position on the same y, but with a x offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive
     * delta will return a terminal position <code>delta</code> number of x to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeX(delta: Int) = if (delta == 0) this else withX(x + delta)

    /**
     * Creates a new [Position] object representing a position on the same x, but with a y offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta
     * will return a terminal position <code>delta</code> number of y to the down and for negative
     * numbers the same up.
     */
    fun withRelativeY(delta: Int) = if (delta == 0) this else withY(y + delta)

    /**
     * Creates a new [Position] object that is translated by an amount of y and x specified by another
     * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
     */
    fun withRelative(translate: Position) = withRelativeY(translate.y).withRelativeX(translate.x)

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    fun relativeToTopOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.of(compX + x, maxOf(compY - y, 0))
    }

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToRightOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.of(
                x = compX + component.getBoundableSize().xLength + x,
                y = compY + y)
    }

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToBottomOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.of(
                x = compX + x,
                y = compY + component.getBoundableSize().yLength + y)
    }

    /**
     * Creates a [Position] which is relative to the left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    fun relativeToLeftOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.of(maxOf(compX - x, 0), compY + y)
    }

    /**
     * Transforms this [Position] to a [Size] so if
     * this position is Position(x=2, y=3) it will become
     * Size(x=2, y=3).
     */
    fun toSize() = Size.of(x, y)

    /////////////////////////////
    /// DEPRECATED ZONE
    ///
    /// HERE BE DRAGONS
    /////////////////////////////

    @Deprecated(message = "This is obsolete, use the value `x` instead",
            replaceWith = ReplaceWith(
                    expression = "x",
                    imports = ["org.codetome.zircon.api"]))
    fun getColumn() = x

    @Deprecated(message = "This is obsolete, use the value `y` instead",
            replaceWith = ReplaceWith(
                    expression = "y",
                    imports = ["org.codetome.zircon.api"]))
    fun getRow() = y

    @Deprecated(message = "This is obsolete, use `withY` instead",
            replaceWith = ReplaceWith(
                    expression = ".withY",
                    imports = ["org.codetome.zircon.api"]))
    fun withRow(row: Int) = withY(row)

    @Deprecated(message = "This is obsolete, use `withX` instead",
            replaceWith = ReplaceWith(
                    expression = ".withX",
                    imports = ["org.codetome.zircon.api"]))
    fun withColumn(column: Int) = withX(column)

    @Deprecated(message = "This is obsolete, use `withRelativeX` instead",
            replaceWith = ReplaceWith(
                    expression = ".withRelativeX",
                    imports = ["org.codetome.zircon.api"]))
    fun withRelativeColumn(delta: Int) = withRelativeX(delta)

    @Deprecated(message = "This is obsolete, use `withRelativeY` instead",
            replaceWith = ReplaceWith(
                    expression = ".withRelativeY",
                    imports = ["org.codetome.zircon.api"]))
    fun withRelativeRow(delta: Int) = withRelativeY(delta)

    companion object {

        private fun generateCacheKey(x: Int, y: Int) = "Position-$x-$y"
        private var hits = 0

        private val cache = DefaultCache<Position>()

        /**
         * Constant for the top-left corner (0x0)
         */
        @JvmField
        val TOP_LEFT_CORNER = Position(0, 0)

        /**
         * Constant for the 1x1 position (one offset in both directions from top-left)
         */
        @JvmField
        val OFFSET_1x1 = Position(1, 1)

        /**
         * This position can be considered as the default
         */
        @JvmField
        val DEFAULT_POSITION = TOP_LEFT_CORNER

        /**
         * Used in place of a possible null value. Means that the position is unknown (cursor for example)
         */
        @JvmField
        val UNKNOWN = Position(Int.MAX_VALUE, Int.MAX_VALUE)

        /**
         * Factory method for [Position].
         */
        @JvmStatic
        fun of(x: Int, y: Int): Position {
            return cache.retrieveIfPresent(generateCacheKey(x, y)).orElseGet {
                cache.store(Position(x, y))
            }
        }
    }
}

