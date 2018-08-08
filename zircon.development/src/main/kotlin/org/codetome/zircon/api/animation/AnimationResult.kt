package org.codetome.zircon.api.animation

/**
 * Represents an eventual result of an [Animation].
 */
interface AnimationResult {

    /**
     * Tells whether the animation is finished.
     */
    fun isFinished(): Boolean

    /**
     * Tells whether the animation is still running.
     */
    fun isRunning(): Boolean

    /**
     * Tells whether this animation will run forever.
     */
    fun isInfinite(): Boolean

    /**
     * Adds a callback which will be called when the [Animation] finishes.
     */
    fun onFinished(fn: (AnimationResult) -> Unit)

    /**
     * Stops the [Animation] regardless of its state.
     */
    fun stop()

}
