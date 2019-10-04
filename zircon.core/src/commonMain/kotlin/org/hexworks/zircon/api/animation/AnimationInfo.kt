package org.hexworks.zircon.api.animation

/**
 * Stores information about an [Animation] which is currently running.
 */
interface AnimationInfo {

    /**
     * Tells whether the [Animation] is finished.
     */
    fun isFinished(): Boolean

    /**
     * Tells whether the [Animation] is still running.
     */
    fun isRunning(): Boolean

    /**
     * Tells whether this [Animation] will run forever.
     */
    fun isInfinite(): Boolean

    /**
     * Adds a callback which will be called when the [Animation] finishes.
     */
    fun onFinished(fn: (AnimationInfo) -> Unit)

    /**
     * Stops the [Animation] regardless of its state.
     */
    fun stop()

}
