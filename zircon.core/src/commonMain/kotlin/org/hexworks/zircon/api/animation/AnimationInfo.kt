package org.hexworks.zircon.api.animation

/**
 * Stores information about an [Animation] which is currently running.
 */
interface AnimationInfo {

    /**
     * Tells whether the [Animation] is finished.
     */
    val isFinished: Boolean

    /**
     * Tells whether the [Animation] is still running.
     */
    val isRunning: Boolean

    /**
     * Tells whether this [Animation] will run forever.
     */
    val isInfinite: Boolean

    /**
     * Adds a callback which will be called when the [Animation] finishes.
     */
    fun onFinished(fn: (AnimationInfo) -> Unit)

    /**
     * Stops the [Animation] regardless of its state.
     */
    fun stop()

}
