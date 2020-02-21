package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationRunner
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable

/**
 * Specialized [AnimationRunner] which is used by Zircon internally.
 */
interface InternalAnimationRunner : AnimationRunner, Closeable {

    /**
     * Updates the [Animation]s this [AnimationRunner] has
     * with the given [currentTimeMs] using the given [layerable].
     */
    fun updateAnimations(currentTimeMs: Long, layerable: Layerable)

    /**
     * Stops the given [InternalAnimation].
     */
    fun stop(animation: InternalAnimation)
}
