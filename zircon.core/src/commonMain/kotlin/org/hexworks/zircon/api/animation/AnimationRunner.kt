@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.Beta

/**
 * The [AnimationRunner] can be used to [start] running [Animation]s.
 * When an [Animation] is started it will be displayed on the screen.
 */
@Beta
interface AnimationRunner {

    /**
     * Adds and starts the given [Animation].
     * @return an [AnimationHandle] which can be used to
     * interact with the running [Animation].
     */
    fun start(animation: Animation): AnimationHandle
}
