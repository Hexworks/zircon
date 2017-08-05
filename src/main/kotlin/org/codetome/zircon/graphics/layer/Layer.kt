package org.codetome.zircon.graphics.layer

import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.screen.Screen

/**
 * A [Layer] is a specialized [TextImage] which is drawn upon (or below) a
 * [Layerable] object. It supports some additional
 * operations to determine the position of the [Layer] relative to its container
 * (which is most likely a [Screen]).
 */
interface Layer : TextImage {

    /**
     * Returns the offset (columns / rows) where this [Layer] is located
     * relative to the top left position of its parent [Layerable].
     */
    fun getOffset(): Size

    /**
     * Sets the offset position.
     */
    fun setOffset(offset: Size)
}