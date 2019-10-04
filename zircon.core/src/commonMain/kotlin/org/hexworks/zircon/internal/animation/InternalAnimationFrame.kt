package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.behavior.Layerable

internal interface InternalAnimationFrame : AnimationFrame {

    /**
     * Displays this [AnimationFrame] on the given [layerable].
     */
    fun displayOn(layerable: Layerable)

    /**
     * Removes this [AnimationFrame] from the display it is visible on (if any).
     */
    fun remove()
}
