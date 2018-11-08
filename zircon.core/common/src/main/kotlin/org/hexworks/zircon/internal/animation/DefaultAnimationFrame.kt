package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.util.Maybe

/**
 * Default implementation of the [AnimationFrame] interface.
 */
data class DefaultAnimationFrame(override val size: Size,
                                 override val layers: List<Layer>,
                                 override val repeatCount: Int) : InternalAnimationFrame {

    override var position: Position = Position.defaultPosition()
        set(value) {
            field = value
            layers.forEach { it.moveTo(value) }
        }

    private var displayedOn: Maybe<TileGrid> = Maybe.empty()

    override fun displayOn(tileGrid: TileGrid) {
        remove()
        layers.forEach { layer ->
            tileGrid.pushLayer(layer)
        }
    }

    override fun remove() {
        displayedOn.map { prevDisplay ->
            layers.forEach(prevDisplay::removeLayer)
        }
    }
}
