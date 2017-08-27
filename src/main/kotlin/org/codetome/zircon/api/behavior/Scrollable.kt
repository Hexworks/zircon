package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size

/**
 * A [Scrollable]
 */
interface Scrollable : CursorHandler {

    /**
     * Returns the [Size] of the visible space this [Scrollable] occupies.
     */
    fun getVisibleSpaceSize(): Size

    /**
     * Returns the offset where the visible part of this [Scrollable] starts.
     */
    fun getVisibleOffset(): Position

    /**
     * Sets the offset where the visible part of this [Scrollable] starts.
     */
    fun setVisibleOffset(offset: Position)
}