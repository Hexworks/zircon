package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size

/**
 * Base class for [Rect] implementations.
 */
abstract class BaseRect : Rect {

    override val x
        get() = position.x

    override val y
        get() = position.y

    override val width
        get() = size.width

    override val height
        get() = size.height

    override val topLeft: Position
        get() = position

    override val topCenter: Position
        get() = position.withRelativeX(width / 2)

    override val topRight: Position
        get() = position.withRelativeX(width)

    override val rightCenter: Position
        get() = position.withRelativeX(width).withRelativeY(height / 2)

    override val bottomRight: Position
        get() = position + size.toPosition()

    override val bottomCenter: Position
        get() = position.withRelativeY(height).withRelativeX(width / 2)

    override val bottomLeft: Position
        get() = position.withRelativeY(height)

    override val leftCenter: Position
        get() = position.withRelativeY(height / 2)

    override val center: Position
        get() = position.withRelativeX(width / 2).withRelativeY(height / 2)

    override operator fun component1() = x

    override operator fun component2() = y

    override operator fun component3() = width

    override operator fun component4() = height

    override operator fun plus(rect: Rect) = Rect.create(
            position = position + rect.position,
            size = size + rect.size)

    override operator fun minus(rect: Rect) = Rect.create(
            position = position - rect.position,
            size = size - rect.size)

    override fun intersects(boundable: Boundable): Boolean {
        val otherBounds = boundable.rect
        var tw = size.width
        var th = size.height
        var rw = otherBounds.width
        var rh = otherBounds.height
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = this.x
        val ty = this.y
        val rx = otherBounds.x
        val ry = otherBounds.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return (rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry)
    }

    override fun containsPosition(position: Position): Boolean {
        val (otherX, otherY) = position
        var width = width
        var height = height
        if (width or height < 0) {
            return false
        }
        val x = x
        val y = y
        if (otherX < x || otherY < y) {
            return false
        }
        width += x
        height += y
        return (width < x || width > otherX) && (height < y || height > otherY)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        var (otherX, otherY, otherWidth, otherHeight) = boundable.rect
        var w = width
        var h = height
        val x = x
        val y = y
        if (w or h or otherWidth or otherHeight < 0) {
            return false
        }
        if (otherX < x || otherY < y) {
            return false
        }
        w += x
        otherWidth += otherX
        if (otherWidth <= otherX) {
            if (w >= x || otherWidth > w) return false
        } else {
            if (w in x..(otherWidth - 1)) return false
        }
        h += y
        otherHeight += otherY
        if (otherHeight <= otherY) {
            if (h >= y || otherHeight > h) return false
        } else {
            if (h in y..(otherHeight - 1)) return false
        }
        return true
    }

    override fun splitHorizontal(splitAtX: Int): Pair<Rect, Rect> {
        var left = Rect.create(Position.create(x, y), Sizes.create(splitAtX, height))
        var right = Rect.create(Position.create(x + splitAtX + 1, y), Sizes.create(width - splitAtX - 1, height))
        return left to right
    }

    override fun splitVertical(splitAtY: Int): Pair<Rect, Rect> {
        var left = Rect.create(Position.create(x, y), Sizes.create(width, splitAtY))
        var right = Rect.create(Position.create(x , y + splitAtY + 1), Sizes.create(width, height - splitAtY - 1))
        return left to right
    }

    override fun withPosition(position: Position) = Rect.create(position, size)

    override fun withSize(size: Size) = Rect.create(position, size)

    override fun withRelativePosition(position: Position) = Rect.create(this.position + position, size)

    override fun withRelativeSize(size: Size) = Rect.create(position, this.size + size)
}
