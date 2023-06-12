package org.hexworks.zircon.api.animation

import org.hexworks.zircon.internal.behavior.Identifiable

/**
 * Represents a series of [AnimationFrame]s that when drawn on the screen
 * after each other become an animation.
 */

interface Animation : Identifiable {

    /**
     * The number of [AnimationFrame]s that are in this animation.
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
}
