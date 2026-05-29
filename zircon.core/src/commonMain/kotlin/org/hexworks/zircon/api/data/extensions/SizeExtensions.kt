package org.hexworks.zircon.api.data.extensions

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.shape.RectangleFactory
import kotlin.math.max
import kotlin.math.min

/**
 * Tells whether this [Size] **is** the same as [Size.unknown].
 */
val Size.isSizeUnknown: Boolean
    get() = this === Size.UNKNOWN

/**
 * Tells whether this [Size] **is not** the same as [Size.unknown].
 */
val Size.isSizeNotUnknown: Boolean
    get() = this !== Size.UNKNOWN

/**
 * Returns this [Size] or [other] if this size [isSizeUnknown].
 */
fun Size.orElse(other: Size) = if (isSizeUnknown) other else this

/**
 * Creates a list of [Position]s in the order in which they should
 * be iterated when drawing (first rows, then columns in those rows).
 */
//! TODO: make this a property instead of a function
fun Size.fetchPositions(): Iterable<Position> = Iterable {
    var currY = 0
    var currX = 0
    val endX = width
    val endY = height

    object : Iterator<Position> {

        override fun hasNext() = currY < endY && currX < endX

        override fun next(): Position {
            return Position.create(currX, currY).also {
                currX++
                if (currX == endX) {
                    currY++
                    if (currY < endY) {
                        currX = 0
                    }
                }
            }
        }
    }
}

/**
 * Creates a list of [Position]s which represent the
 * bounding box of this size. So for example a size of (3x3)
 * will have a bounding box of
 * `[(0, 0), (1, 0), (2, 0), (0, 1), (2, 1), (0, 2), (1, 2), (2, 2)]`
 */
fun Size.fetchBoundingBoxPositions(): Set<Position> {
    return RectangleFactory
        .buildRectangle(Position.ZERO, this)
        .positions
}

fun Size.fetchTopLeftPosition(): Position = Position.ZERO

fun Size.fetchTopRightPosition(): Position = Position.create(width - 1, 0)

fun Size.fetchBottomLeftPosition(): Position = Position.create(0, height - 1)

fun Size.fetchBottomRightPosition(): Position = Position.create(width - 1, height - 1)

/**
 * Creates a new size based on this size, but with a different width.
 */
fun Size.withWidth(width: Int): Size {
    if (this.width == width) {
        return this
    }
    return Size.create(width, this.height)
}

/**
 * Creates a new size based on this size, but with a different height.
 */
fun Size.withHeight(height: Int): Size {
    if (this.height == height) {
        return this
    }
    return Size.create(this.width, height)
}

/**
 * Creates a new [Size] object representing a size with the same number of height, but with
 * a width size offset by a supplied value. Calling this method with delta 0 will return this,
 * calling it with a positive delta will return
 * a grid size <code>delta</code> number of width wider and for negative numbers shorter.
 */
fun Size.withRelativeWidth(delta: Int): Size {
    if (delta == 0) {
        return this
    }
    return withWidth(width + delta)
}

/**
 * Creates a new [Size] object representing a size with the same number of width, but with a height
 * size offset by a supplied value. Calling this method with delta 0 will return this, calling
 * it with a positive delta will return
 * a grid size <code>delta</code> number of height longer and for negative numbers shorter.
 */
fun Size.withRelativeHeight(delta: Int): Size {
    if (delta == 0) {
        return this
    }
    return this.withHeight(height + delta)
}

/**
 * Creates a new [Size] object representing a size based on this object's size but with a delta applied.
 * This is the same as calling `withRelativeXLength(delta.getXLength()).withRelativeYLength(delta.getYLength())`
 */
fun Size.withRelative(delta: Size): Size {
    return this.withRelativeHeight(delta.height).withRelativeWidth(delta.width)
}

/**
 * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
 * measured separately. So calling 3x5 on a 5x3 will return 5x5.
 */
fun Size.max(other: Size): Size {
    return withWidth(max(width, other.width))
        .withHeight(max(height, other.height))
}

/**
 * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
 * measured separately. So calling 3x5 on a 5x3 will return 3x3.
 */
fun Size.min(other: Size): Size {
    return withWidth(min(width, other.width))
        .withHeight(min(height, other.height))
}

/**
 * Returns itself if it is equal to the supplied size, otherwise the supplied size.
 * You can use this if you have a size field which is frequently recalculated but often resolves
 * to the same size; it will keep the same object
 * in memory instead of swapping it out every cycle.
 */
fun Size.with(size: Size): Size {
    if (equals(size)) {
        return this
    }
    return size
}

/**
 * Tells whether this [Size] contains the given [Position].
 * Works in the same way as [Boundable.containsPosition].
 */
fun Size.containsPosition(position: Position): Boolean = position.x < width && position.y < height

/**
 * Converts this [Size] to a [Position]:
 * [Size.width] to [Position.x] and [Size.height] to [Position.y]
 */
fun Size.toPosition(): Position = Position.create(width, height)

/**
 * Converts this [Size] to a [Rect] with the given [Position].
 */
fun Size.toBoundable(position: Position = Position.ZERO): Boundable = Boundable.create(position, this)

/**
 * Creates a new [Size3D] from this [Size] and the given [zLength].
 */
fun Size.toSize3D(zLength: Int = 0) = Size3D.from2DSize(this, zLength)
