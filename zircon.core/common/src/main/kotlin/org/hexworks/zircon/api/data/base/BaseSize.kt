package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.shape.RectangleFactory
import org.hexworks.zircon.api.util.Math

abstract class BaseSize : Size {

    override operator fun plus(other: Size) = Size.create(width + other.width, height + other.height)

    override operator fun minus(other: Size) = Size.create(width - other.width, height - other.height)

    override operator fun component1() = width

    override operator fun component2() = height

    override fun compareTo(other: Size) = (this.width * this.height).compareTo(other.width * other.height)

    override fun isUnknown() = this === Size.unknown()

    override fun isNotUnknown() = this !== Size.unknown()

    override fun fetchPositions(): Iterable<Position> = Iterable {
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

    override fun fetchBoundingBoxPositions(): Set<Position> {
        return RectangleFactory
                .buildRectangle(Position.defaultPosition(), this)
                .positions()
    }

    override fun fetchTopLeftPosition() = Position.topLeftCorner()

    override fun fetchTopRightPosition() = Position.create(width - 1, 0)

    override fun fetchBottomLeftPosition() = Position.create(0, height - 1)

    override fun fetchBottomRightPosition() = Position.create(width - 1, height - 1)

    override fun withWidth(width: Int): Size {
        if (this.width == width) {
            return this
        }
        return Size.create(width, this.height)
    }

    override fun withHeight(height: Int): Size {
        if (this.height == height) {
            return this
        }
        return Size.create(this.width, height)
    }

    override fun withRelativeWidth(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withWidth(width + delta)
    }

    override fun withRelativeHeight(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return this.withHeight(height + delta)
    }

    override fun withRelative(delta: Size): Size {
        return this.withRelativeHeight(delta.height).withRelativeWidth(delta.width)
    }

    override fun max(other: Size): Size {
        return withWidth(Math.max(width, other.width))
                .withHeight(Math.max(height, other.height))
    }

    override fun min(other: Size): Size {
        return withWidth(Math.min(width, other.width))
                .withHeight(Math.min(height, other.height))
    }

    override fun with(size: Size): Size {
        if (equals(size)) {
            return this
        }
        return size
    }

    override fun containsPosition(position: Position) = width > position.x && height > position.y

    override fun toPosition() = Position.create(width, height)

    override fun toRect(): Rect = toRect(Position.defaultPosition())

    override fun toRect(position: Position): Rect = Rect.create(position, this)
}
