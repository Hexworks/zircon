package org.codetome.zircon.api.animation

import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.screen.Screen

/**
 * This component is responsible for running [Animation]s.
 */
interface AnimationHandler : Closeable {

    /**
     * Adds and starts the given [Animation].
     * @return the eventual result of the animation.
     */
    fun addAnimation(animation: Animation): AnimationResult
}
