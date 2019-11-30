package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * A [Scrollable3D] object has a visible 3D space which might be smaller than
 * its real size. A 3D scrollable maintains a visible "cube" over
 * its content which is usually bigger in at least one dimension than the visible
 * part.
 */
interface Scrollable3D {

    /**
     * Returns the [Size3D] of the virtual space this [Scrollable3D] can scroll through.
     */
    val actualSize: Size3D

    /**
     * Returns the size of the visible part of this [Scrollable3D].
     */
    val visibleSize: Size3D

    /**
     * Returns the offset where the visible part of this [Scrollable3D] starts.
     */
    val visibleOffset: Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit to the right (width axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneRight(): Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit to the left (width axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneLeft(): Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit up (height axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneUp(): Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit down (height axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneDown(): Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit forward (depth axis, away from the observer).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneForward(): Position3D

    /**
     * Scrolls this [Scrollable3D] with one unit backward (depth axis, towards the observer).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneBackward(): Position3D

    /**
     * Scrolls this [Scrollable3D] by `width` units to the right (width axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollRightBy(x: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] with `width` units to the left (width axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollLeftBy(x: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] by `height` units up (height axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollUpBy(z: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] with `height` units down (height axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollDownBy(z: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] by `depth` units forward (depth axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollForwardBy(y: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] with `depth` units backward (depth axis).
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollBackwardBy(y: Int): Position3D

    /**
     * Scrolls this [Scrollable3D] to the provided position
     */
    fun scrollTo3DPosition(position3D: Position3D)
}
