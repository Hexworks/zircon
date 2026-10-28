package org.hexworks.zircon.api.animation

/**
 * Represents an [Animation] that is currently running and adds
 * additional functionality to it.
 */

public interface AnimationHandle : Animation {

    /**
     * Tells whether the [Animation] is finished.
     */
    public val isFinished: Boolean

    /**
     * Tells whether the [Animation] is still running.
     */
    public val isRunning: Boolean

    /**
     * Tells whether this [Animation] will run forever.
     */
    public val isInfinite: Boolean

    /**
     * Adds a callback that will be called when the [Animation] finishes.
     */
    public fun onFinished(fn: (AnimationHandle) -> Unit)

    /**
     * Stops the [Animation] regardless of its state.
     */
    public fun stop()

}
