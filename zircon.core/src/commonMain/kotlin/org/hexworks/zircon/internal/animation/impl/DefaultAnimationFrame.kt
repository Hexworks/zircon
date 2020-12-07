package org.hexworks.zircon.internal.animation.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.graphics.InternalLayer

/**
 * Default implementation of the [AnimationFrame] interface.
 */
data class DefaultAnimationFrame(
    override val size: Size,
    override val layers: List<InternalLayer>,
    override val repeatCount: Int
) : InternalAnimationFrame {

    override var position: Position = Position.defaultPosition()
        set(value) {
            field = value
            layers.forEach { it.moveTo(value) }
        }

    private var displayLayerable: Maybe<InternalLayerable> = Maybe.empty()

    override fun displayOn(layerable: Layerable) {
        layerable as? InternalLayerable ?: error(Layerable.WRONG_LAYER_TYPE_MSG)
        val layerableRect = layerable.size.toRect()
        require(layers.all { layerableRect.containsBoundable(it.rect) }) {
            "Can't add Animation to Layerable, because its layers are out of bounds."
        }
        remove()
        this.displayLayerable = Maybe.of(layerable)
        layers.forEach {
            it.moveTo(position)
            layerable.addLayer(it)
        }
    }

    override fun remove() {
        displayLayerable.map { currDisplay ->
            layers.forEach { currDisplay.removeLayer(it) }
            displayLayerable = Maybe.empty()
        }
    }
}
