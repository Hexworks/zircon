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
data class DefaultAnimationFrame(private val size: Size,
                                 private val layers: List<Layer>,
                                 private val repeatCount: Int) : InternalAnimationFrame {

    private var position: Position = Position.defaultPosition()

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

    override fun getSize() = size

    override fun getRepeatCount() = repeatCount

    override fun getLayers() = layers

    override fun getPosition() = position

    override fun setPosition(position: Position) {
        this.position = position
        layers.forEach { it.moveTo(position) }
    }
}
