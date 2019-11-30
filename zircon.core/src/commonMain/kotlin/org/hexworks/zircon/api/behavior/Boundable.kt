package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable

/**
 * Represents an object which has bounds and a position in 2D space.
 * A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable]s (like intersection).
 */
interface Boundable : Sizeable {

    val rect: Rect
    val position: Position
        get() = rect.position
    val x: Int
        get() = rect.x
    val y: Int
        get() = rect.y

    /**
     * Tells whether this [Boundable] intersects with the other [boundable].
     */
    infix fun intersects(boundable: Boundable): Boolean {
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

    /**
     * Tells whether [position] is within this boundable's bounds.
     */
    infix fun containsPosition(position: Position): Boolean {
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

    /**
     * Tells whether this boundable contains the other [boundable].
     * A [Boundable] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same it is considered
     * a containment).
     */
    infix fun containsBoundable(boundable: Boundable): Boolean {
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
            if (w in x until otherWidth) return false
        }
        h += y
        otherHeight += otherY
        if (otherHeight <= otherY) {
            if (h >= y || otherHeight > h) return false
        } else {
            if (h in y until otherHeight) return false
        }
        return true
    }

    companion object {

        fun create(position: Position = Position.defaultPosition(), size: Size): Boundable {
            return DefaultMovable(size, position)
        }
    }
}
