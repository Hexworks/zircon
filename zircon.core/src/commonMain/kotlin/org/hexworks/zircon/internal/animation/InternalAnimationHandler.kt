package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandler
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable

/**
 * Specialized [AnimationHandler] which is used by Zircon internally.
 */
interface InternalAnimationHandler : AnimationHandler, Closeable {

    /**
     * Updates the [Animation]s this [AnimationHandler] has
     * with the given [currentTimeMs] using the given [layerable].
     */
    fun updateAnimations(currentTimeMs: Long, layerable: Layerable)

}
