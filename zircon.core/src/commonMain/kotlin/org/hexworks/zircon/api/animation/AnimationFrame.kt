package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer

/**
 * Stores information about a single animation frame.
 */

interface AnimationFrame {

    val size: Size

    val layers: List<Layer>

    /**
     * How many times this frame will be repeated.
     */
    val repeatCount: Int

    /**
     * The [Position] at which this [AnimationFrame] should be drawn.
     */
    val position: Position
}
