package org.codetome.zircon.api.animation

import java.util.concurrent.TimeUnit

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
     * Waits until the `Animation` is finished or until timeout happens.
     */
    fun waitUntilFinish(timeout: Long, timeUnit: TimeUnit): AnimationResult

}
