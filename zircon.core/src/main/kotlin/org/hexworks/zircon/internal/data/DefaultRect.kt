package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultRect(private val position: Position,
                  private val size: Size) : Rect {

    override fun position() = position

    override fun size() = size

    override fun containsPosition(position: Position): Boolean {
        val (otherX, otherY) = position
        var width = width()
        var height = height()
        if (width or height < 0) {
            return false
        }
        val x = x()
        val y = y()
        if (otherX < x || otherY < y) {
            return false
        }
        width += x
        height += y
        return (width < x || width > otherX) && (height < y || height > otherY)
    }

    override fun intersects(boundable: Boundable): Boolean {
        val otherBounds = boundable.rect()
        var tw = size.width()
        var th = size.height()
        var rw = otherBounds.width()
        var rh = otherBounds.height()
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = this.x()
        val ty = this.y()
        val rx = otherBounds.x()
        val ry = otherBounds.y()
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return (rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry)
    }

    override fun toString(): String {
        return "${this::class.simpleName}(position=${position()}," +
                "size=${size()})"
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        var (otherX, otherY, otherWidth, otherHeight) = boundable.rect()
        var w = width()
        var h = height()
        val x = x()
        val y = y()
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultRect

        if (position != other.position) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }

}
