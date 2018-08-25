package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.Identifiable

/**
 * Represents a series of [AnimationFrame]s which if drawn on the screen
 * after each other become an animation.
 */
interface Animation : Identifiable {

    /**
     * Returns the number of [AnimationFrame]s which are in this animation.
     */
    fun getFrameCount(): Int

    /**
     * Returns how many times this [Animation] will be looped.
     * `0` stands for an infinite loop (continuous animation).
     */
    fun getLoopCount(): Int

    /**
     * Returns whether this [Animation] is looped indefinitely.
     */
    fun isLoopedIndefinitely(): Boolean

    /**
     * Returns the *total* number of frames in this animation
     * (including those which are repeated as well).
     */
    fun getTotalFrameCount(): Int

    /**
     * Tells how often an [AnimationFrame] should be drawn in milliseconds.
     */
    fun getTick(): Long

    /**
     * Returns the next [AnimationFrame] to be drawn.
     */
    fun getCurrentFrame(): AnimationFrame

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
    fun getAllFrames(): List<AnimationFrame>

}
