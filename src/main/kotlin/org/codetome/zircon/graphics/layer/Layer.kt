package org.codetome.zircon.graphics.layer

import org.codetome.zircon.Position
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.screen.Screen

/**
 * A [Layer] is a specialized [TextImage] which is drawn upon a
 * [Layerable] object. A [Layer] differs from a [TextImage] in
 * the way it is handled. It can be repositioned relative to its
 * parent while a [TextImage] cannot.
 */
interface Layer : TextImage {

    /**
     * Sets the offset of this [Layer] relative to its parent.
     */
    fun setOffset(offset: Position)

    /**
     * Returns the [Position] of this [Layer] relative to its parent.
     */
    fun getOffset(): Position
}