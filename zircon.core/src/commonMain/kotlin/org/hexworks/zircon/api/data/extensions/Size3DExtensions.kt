package org.hexworks.zircon.api.data.extensions

import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Size3D.Companion.create

operator fun Size3D.plus(other: Size3D) =
    create(xLength + other.xLength, yLength + other.yLength, zLength + other.zLength)

operator fun Size3D.minus(other: Size3D) =
    create(xLength - other.xLength, yLength - other.yLength, zLength - other.zLength)


/**
 * Creates a collection of [Position3D]s in the order in which they should
 * be iterated when drawing:
 * - from bottom to top (z-axis),
 * - from furthest to closest (y-axis),
 * - from left to right (x-axis)
 */
fun Size3D.fetchPositions(): Sequence<Position3D> {
    return sequence {
        (0 until zLength).flatMap { z ->
            (0 until yLength).flatMap { y ->
                (0 until xLength).map { x ->
                    yield(Position3D.create(x, y, z))
                }
            }
        }
    }
}

/**
 * Tells whether this [Size3D] contains the given [Position3D].
 */
fun Size3D.containsPosition(position: Position3D): Boolean {
    val (x, y, z) = position
    return x in 0 until xLength && yLength > y && y >= 0 && zLength > z && z >= 0
}

/**
 * Transforms this [Size3D] to a [Size]. Note that
 * the `zLength` component is lost during the conversion!
 */
fun Size3D.to2DSize() = Size.create(xLength, yLength)


