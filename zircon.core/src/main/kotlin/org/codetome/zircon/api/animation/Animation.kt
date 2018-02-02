package org.codetome.zircon.api.animation

import org.codetome.zircon.api.Beta
import org.codetome.zircon.internal.behavior.Identifiable
import java.util.*

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Beta
interface Animation : Identifiable {

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

    fun getCurrentFrame() : AnimationFrame

    fun fetchNextFrame(): Optional<AnimationFrame>

    fun hasNextFrame(): Boolean

    fun getAllFrames(): List<AnimationFrame>
}
