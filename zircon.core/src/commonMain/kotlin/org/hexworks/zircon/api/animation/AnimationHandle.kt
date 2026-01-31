package org.hexworks.zircon.api.animation

/**
 * Represents an [Animation] that is currently running and adds
 * additional functionality to it.
 */

interface AnimationHandle : Animation {

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
     * Adds a callback that will be called when the [Animation] finishes.
     */
    fun onFinished(fn: (AnimationHandle) -> Unit)

    /**
     * Stops the [Animation] regardless of its state.
     */
    fun stop()

}
