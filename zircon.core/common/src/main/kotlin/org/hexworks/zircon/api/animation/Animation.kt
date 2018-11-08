package org.hexworks.zircon.api.animation

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.internal.behavior.Identifiable

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
     * Returns the next [AnimationFrame] to be drawn.
     */
    fun fetchCurrentFrame(): AnimationFrame

    /**
     * Returns whether this [Animation] is looped indefinitely.
     */
    fun isLoopedIndefinitely(): Boolean

    /**
     * Removes the current frame from the screen (if it is displayed)
     */
    fun clearCurrentFrame()

    /**
     * Returns the next frame to be drawn (if any).
     */
    fun fetchNextFrame(): Maybe<out AnimationFrame>

    /**
     * Tells whether this [Animation] has any frames left.
     */
    fun hasNextFrame(): Boolean

    /**
     * Returns all the [AnimationFrame]s of this [Animation].
     */
    fun fetchAllFrames(): List<AnimationFrame>

}
