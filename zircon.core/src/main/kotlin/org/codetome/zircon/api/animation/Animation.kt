package org.codetome.zircon.api.animation

import org.codetome.zircon.internal.behavior.Identifiable
import org.codetome.zircon.api.util.Maybe

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
interface Animation : Identifiable {

    /**
     *
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

    fun getLength(): Int

    fun getTick(): Long

    fun getCurrentFrame(): AnimationFrame

    fun fetchNextFrame(): Maybe<AnimationFrame>

    fun hasNextFrame(): Boolean

    fun getAllFrames(): List<AnimationFrame>
}
