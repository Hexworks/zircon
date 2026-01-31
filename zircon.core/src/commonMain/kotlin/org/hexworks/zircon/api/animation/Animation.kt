package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.behavior.Identifiable
import kotlin.time.Duration

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
     * The *total* number of frames in this animation
     * (including those which are repeated as well).
     */
    val totalFrameCount: Int

    /**
     * The kind of loop this animation has.
     * Possible options are [InfiniteLoop, FiniteLoop]
     * @see LoopKind
     */
    val loopKind: LoopKind

    /**
     * Tells how often an [AnimationFrame] should be drawn.
     */
    val tick: Duration

    /**
     * Tells whether this [Animation] is looped indefinitely
     * (e.g.: [loopKind] is [InfiniteLoop])
     */
    val isLoopedIndefinitely: Boolean

    sealed class LoopKind {
        companion object
    }

    data object InfiniteLoop : LoopKind()

    data class FiniteLoop(val count: Int) : LoopKind()
}
