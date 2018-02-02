package org.codetome.zircon.api.animation

import java.util.concurrent.TimeUnit

interface AnimationResult {

    fun isFinished(): Boolean

    fun isRunning(): Boolean

    fun isInfinite(): Boolean

    /**
     * Waits until the `Animation` is finished or until timeout happens.
     */
    fun waitUntilFinish(timeout: Long, timeUnit: TimeUnit): AnimationResult

}
