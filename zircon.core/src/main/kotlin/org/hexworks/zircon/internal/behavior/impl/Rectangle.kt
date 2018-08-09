package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position

data class Rectangle(
        var x: Int = 0,
        var y: Int = 0,
        var width: Int = 0,
        var height: Int = 0) {

    fun intersects(r: Rectangle): Boolean {
        var tw = this.width
        var th = this.height
        var rw = r.width
        var rh = r.height
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = this.x
        val ty = this.y
        val rx = r.x
        val ry = r.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return (rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry)
    }

    fun contains(position: Position): Boolean {
        val (px, py) = position
        var w = this.width
        var h = this.height
        if (w or h < 0) {
            return false
        }
        val x = this.x
        val y = this.y
        if (px < x || py < y) {
            return false
        }
        w += x
        h += y
        return (w < x || w > px) && (h < y || h > py)
    }

    fun contains(rectangle: Rectangle): Boolean {
        var (otherX, otherY, otherWidth, otherHeight) = rectangle
        var w = this.width
        var h = this.height
        if (w or h or otherWidth or otherHeight < 0) {
            return false
        }
        val x = this.x
        val y = this.y
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

}
