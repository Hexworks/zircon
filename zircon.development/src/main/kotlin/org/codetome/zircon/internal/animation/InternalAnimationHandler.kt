package org.codetome.zircon.internal.animation

import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.animation.AnimationHandler
import org.codetome.zircon.api.grid.TileGrid

interface InternalAnimationHandler : AnimationHandler {

    /**
     * Updates the [Animation]s this [AnimationHandler] has
     * with the given `currentTimeMs` using the given `tileGrid`.
     */
    fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid)
}
