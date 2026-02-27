package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.behavior.extensions.x
import org.hexworks.zircon.api.behavior.extensions.y
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.DEFAULT_POSITION
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.data.DefaultBoundable

/**
 * Represents an object that has bounds and a position in 2D space. A [Boundable] object
 * can provide useful information about its geometry relating to other [Boundable]s
 * (like intersection).
 */
interface Boundable : HasSize {

    val position: Position
    override val size: Size

    operator fun component1() = position
    operator fun component2() = size

    operator fun plus(other: Boundable) = DefaultBoundable(
        position = position + other.position,
        size = size + other.size
    )

    operator fun minus(other: Boundable) = DefaultBoundable(
        position = position - other.position,
        size = size - other.size
    )

    /**
     * Tells whether this [Boundable] intersects with the other [other].
     */
    infix fun intersects(other: Boundable): Boolean {
        var tw = size.width
        var th = size.height
        var rw = other.width
        var rh = other.height
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = this.x
        val ty = this.y
        val rx = other.x
        val ry = other.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return (rw !in rx..tx) &&
                (rh !in ry..ty) &&
                (tw !in tx..rx) &&
                (th !in ty..ry)
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
        return (width !in x..otherX) && (height !in y..otherY)
    }

    /**
     * Tells whether this boundable contains the other [other].
     * A [Boundable] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same, it is considered
     * containment).
     */
    infix fun containsBoundable(other: Boundable): Boolean {
        val (otherPos, otherSize) = other
        var (otherX, otherY) = otherPos
        var (otherWidth, otherHeight) = otherSize
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
            if (w !in otherWidth..<x) return false
        } else {
            if (w in x until otherWidth) return false
        }
        h += y
        otherHeight += otherY
        if (otherHeight <= otherY) {
            if (h !in otherHeight..<y) return false
        } else {
            if (h in y until otherHeight) return false
        }
        return true
    }

    companion object {

        fun create(position: Position = DEFAULT_POSITION, size: Size): Boundable {
            return DefaultBoundable(position, size)
        }
    }
}
