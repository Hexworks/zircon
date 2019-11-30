package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.jvm.JvmStatic

/**
 * Represents a series of [AnimationFrame]s which if drawn on the screen
 * after each other become an animation.
 */
interface Animation : Identifiable {

    /**
     * The number of [AnimationFrame]s which are in this animation.
     */
    val uniqueFrameCount: Int

    /**
     * How many times this [Animation] will be looped.
     * `0` stands for an infinite loop (continuous animation).
     */
    val loopCount: Int

    /**
     * The *total* number of frames in this animation
     * (including those which are repeated as well).
     */
    val totalFrameCount: Int

    /**
     * Tells how often an [AnimationFrame] should be drawn in milliseconds.
     */
    val tick: Long

    /**
     * Tells whether this [Animation] is looped indefinitely.
     */
    val isLoopedIndefinitely: Boolean

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

    companion object {

        @JvmStatic
        fun newBuilder() = AnimationBuilder.newBuilder()
    }
}
