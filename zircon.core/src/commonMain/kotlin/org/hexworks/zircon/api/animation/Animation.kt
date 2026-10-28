package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.behavior.Identifiable
import kotlin.time.Duration

/**
 * Represents a series of [AnimationFrame]s that when drawn on the screen
 * after each other become an animation.
 */

public interface Animation : Identifiable {

    /**
     * The number of [AnimationFrame]s that are in this animation.
     */
    public val uniqueFrameCount: Int

    /**
     * The *total* number of frames in this animation
     * (including those which are repeated as well).
     */
    public val totalFrameCount: Int

    /**
     * The kind of loop this animation has.
     * Possible options are [InfiniteLoop, FiniteLoop]
     * @see LoopKind
     */
    public val loopKind: LoopKind

    /**
     * Tells how often an [AnimationFrame] should be drawn.
     */
    public val tick: Duration

    /**
     * Tells whether this [Animation] is looped indefinitely
     * (e.g.: [loopKind] is [InfiniteLoop])
     */
    public val isLoopedIndefinitely: Boolean

    public sealed class LoopKind {
        public companion object
    }

    public data object InfiniteLoop : LoopKind()

    public data class FiniteLoop(val count: Int) : LoopKind() {
        init {
            require(count > 0) { "Loop count must be greater than 0!" }
        }
    }
}
