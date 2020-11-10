@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.jvm.JvmStatic

/**
 * Represents a series of [AnimationFrame]s which if drawn on the screen
 * after each other become an animation.
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
@Beta
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

    companion object {

        @JvmStatic
        fun newBuilder() = AnimationBuilder.newBuilder()
    }
}
