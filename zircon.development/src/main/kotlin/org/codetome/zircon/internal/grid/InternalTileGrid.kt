package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.animation.InternalAnimationHandler

interface InternalTileGrid
    : TileGrid, InternalAnimationHandler {

    var backend: TileImage
    var layerable: Layerable
    var animationHandler: InternalAnimationHandler

    fun useContentsOf(tileGrid: InternalTileGrid)

    fun reset()

}
