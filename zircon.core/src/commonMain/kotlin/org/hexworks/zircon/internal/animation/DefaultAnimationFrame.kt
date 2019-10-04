package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer

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

    private var displayLayerable: Maybe<Layerable> = Maybe.empty()

    override fun displayOn(layerable: Layerable) {
        remove()
        this.displayLayerable = Maybe.of(layerable)
        layers.forEach {
            it.moveTo(position)
            layerable.addLayer(it)
        }
    }

    override fun remove() {
        displayLayerable.map { currDisplay ->
            layers.forEach(currDisplay::removeLayer)
            displayLayerable = Maybe.empty()
        }
    }
}
