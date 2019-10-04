package org.hexworks.zircon.api.animation

/**
 * Represents an object which is responsible for running [Animation]s.
 */
interface AnimationHandler {

    /**
     * Adds and starts the given [Animation].
     */
    fun startAnimation(animation: Animation): AnimationInfo

    /**
     * Stops the given [Animation] even if it is an infinite one.
     */
    fun stopAnimation(animation: Animation)
}
