package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Position

public interface InternalAnimationFrame : AnimationFrame {

    /**
     * The [Position] at which this [AnimationFrame] should be drawn.
     */
    public var position: Position

    /**
     * Displays this [AnimationFrame] on the given [layerable].
     */
    public fun displayOn(layerable: Layerable)

    /**
     * Removes this [AnimationFrame] from the display it is visible on (if any).
     */
    public fun remove()
}
