package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.CursorHandler

/**
 * A [Scrollable] object has a visible space which might be smaller than
 * its real size. A scrollable is supposed to maintain a visible "window" over
 * its content which is usually bigger either vertically or horizontally than
 * its visible part.
 */
interface Scrollable : CursorHandler {

    /**
     * Returns the [Size] of the virtual space this [Scrollable] can scroll through.
     */
    fun getVirtualSpaceSize(): Size

    /**
     * Sets the [Size] of the virtual space this [Scrollable] can scroll through.
     */
    fun setVirtualSpaceSize(size: Size)

    /**
     * Returns the offset where the visible part of this [Scrollable] starts.
     */
    fun getVisibleOffset(): Position

    fun scrollOneRight()

    fun scrollOneLeft()

    fun scrollOneUp()

    fun scrollOneDown()
}