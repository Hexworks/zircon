package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.behavior.Layerable

interface InternalAnimation : Animation {

    /**
     * Displays the next frame on the given [layerable]. If there
     * are no frames left to display [displayNextFrame] will
     * return `false`
     * @return `true` if a frame was displayed, `false` if not.
     */
    fun displayNextFrame(layerable: Layerable): Boolean

    /**
     * Removes the current frame from the screen (if it is displayed).
     */
    fun removeCurrentFrame()

}