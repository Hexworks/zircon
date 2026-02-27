package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.extensions.containsPosition
import org.hexworks.zircon.api.data.extensions.withRelativeZ
import kotlin.math.max
import kotlin.math.min

/**
 * Scrolls this [Scrollable3D] with one unit to the right (width axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneRight() = Position3D.from2DPosition(
    position = scrollable2D.scrollOneRight(),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] with one unit to the left (width axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneLeft() = Position3D.from2DPosition(
    position = scrollable2D.scrollOneLeft(),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] with one unit up (height axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneUp(): Position3D {
    if (visibleSize.zLength + visibleOffset.z < actualSize.zLength) {
        this.visibleOffset = visibleOffset.withRelativeZ(1)
    }
    return visibleOffset
}

/**
 * Scrolls this [Scrollable3D] with one unit down (height axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneDown(): Position3D {
    if (visibleOffset.z > 0) {
        visibleOffset = visibleOffset.withRelativeZ(-1)
    }
    return visibleOffset
}

/**
 * Scrolls this [Scrollable3D] with one unit forward (depth axis, away from the observer).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneForward() = Position3D.from2DPosition(
    position = scrollable2D.scrollOneDown(),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] with one unit backward (depth axis, towards the observer).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollOneBackward(): Position3D = Position3D.from2DPosition(
    position = scrollable2D.scrollOneUp(),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] by `width` units to the right (width axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollRightBy(x: Int): Position3D = Position3D.from2DPosition(
    position = scrollable2D.scrollRightBy(x),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] with `width` units to the left (width axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollLeftBy(x: Int): Position3D = Position3D.from2DPosition(
    position = scrollable2D.scrollLeftBy(x),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] by `height` units up (height axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollUpBy(z: Int): Position3D {
    require(z >= 0) {
        "You can only scroll up by a positive amount!"
    }
    val levelToScrollTo = visibleOffset.z + z
    val lastScrollableLevel = actualSize.zLength - visibleSize.zLength
    visibleOffset = visibleOffset.copy(z = min(levelToScrollTo, lastScrollableLevel))
    return visibleOffset
}

/**
 * Scrolls this [Scrollable3D] with `height` units down (height axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollDownBy(z: Int): Position3D {
    require(z >= 0) {
        "You can only scroll down by a positive amount!"
    }
    val levelToScrollTo = visibleOffset.z - z
    visibleOffset = visibleOffset.copy(z = max(0, levelToScrollTo))
    return visibleOffset
}

/**
 * Scrolls this [Scrollable3D] by `depth` units forward (depth axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollForwardBy(y: Int): Position3D = Position3D.from2DPosition(
    position = scrollable2D.scrollDownBy(y),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] with `depth` units backward (depth axis).
 * If the bounds of the virtual space are already reached this method has no effect.
 * @return the new visible offset
 */
fun Scrollable3D.scrollBackwardBy(y: Int): Position3D = Position3D.from2DPosition(
    position = scrollable2D.scrollUpBy(y),
    z = visibleOffset.z
).apply {
    visibleOffset = this
}

/**
 * Scrolls this [Scrollable3D] to the provided position
 */
fun Scrollable3D.scrollTo(position3D: Position3D) {
    require(actualSize.containsPosition(position3D))
    {
        "new position $position3D has to be within the actual size $actualSize"
    }
    visibleOffset = position3D
}
