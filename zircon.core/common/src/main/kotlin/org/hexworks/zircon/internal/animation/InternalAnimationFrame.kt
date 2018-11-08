package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.grid.TileGrid

interface InternalAnimationFrame : AnimationFrame {

    /**
     * Displays this [AnimationFrame] on the given [TileGrid].
     */
    fun displayOn(tileGrid: TileGrid)

    /**
     * Removes this [AnimationFrame] from the display it is visible on (if any).
     */
    fun remove()
}
