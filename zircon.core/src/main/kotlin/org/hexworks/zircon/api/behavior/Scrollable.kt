package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.Scrollable3D

/**
 * A [Scrollable] object has a visible 2D space which might be smaller than
 * its virtual size. A scrollable is supposed to maintain a visible "window" over
 * its content which is usually bigger either vertically or horizontally than
 * its visible part.
 */
interface Scrollable {

    /**
     * Returns the [Size] of the virtual space this [Scrollable] can scroll through.
     */
    fun actualSize(): Size

    /**
     * Sets the [Size] of the virtual space this [Scrollable] can scroll through.
     */
    fun setActualSize(size: Size)

    /**
     * Returns the size of the visible part of this [Scrollable3D].
     */
    fun visibleSize(): Size

    /**
     * Returns the offset where the visible part of this [Scrollable] starts.
     */
    fun visibleOffset(): Position

    /**
     * Scrolls this [Scrollable] with one width to the right.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneRight(): Position

    /**
     * Scrolls this [Scrollable] with one width to the left.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneLeft(): Position

    /**
     * Scrolls this [Scrollable] with one depth up.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneUp(): Position

    /**
     * Scrolls this [Scrollable] with one depth down.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollOneDown(): Position

    /**
     * Scrolls this [Scrollable] by `width` width to the right.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollRightBy(columns: Int): Position

    /**
     * Scrolls this [Scrollable] with `width` width to the left.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollLeftBy(columns: Int): Position

    /**
     * Scrolls this [Scrollable] by `depth` depth up.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollUpBy(rows: Int): Position


    /**
     * Scrolls this [Scrollable] with `depth` depth down.
     * If the bounds of the virtual space are already reached this method has no effect.
     * @return the new visible offset
     */
    fun scrollDownBy(rows: Int): Position

}
