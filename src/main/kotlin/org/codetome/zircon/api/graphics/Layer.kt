package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.behavior.Movable

/**
 * A [Layer] is a specialized [TextImage] which is drawn upon a
 * [Layerable] object. A [Layer] differs from a [TextImage] in
 * the way it is handled. It can be repositioned relative to its
 * parent while a [TextImage] cannot.
 */
interface Layer : TextImage, Movable {

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] occupies.
     */
    fun fetchPositions(): Set<Position>

    /**
     * Copies this [Layer].
     */
    fun createCopy(): Layer
}