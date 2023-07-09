package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Position

interface InternalAnimationFrame : AnimationFrame {

    /**
     * The [Position] at which this [AnimationFrame] should be drawn.
     */
    var position: Position

    /**
     * Displays this [AnimationFrame] on the given [layerable].
     */
    fun displayOn(layerable: Layerable)

    /**
     * Removes this [AnimationFrame] from the display it is visible on (if any).
     */
    fun remove()
}
