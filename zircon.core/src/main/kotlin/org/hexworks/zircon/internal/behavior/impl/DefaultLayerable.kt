package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultLayerable(
        size: Size,
        boundable: Boundable = DefaultBoundable(size))
    : Layerable, Boundable by boundable {

    override val layers: List<Layer>
        get() = currentLayers.toList()

    private val currentLayers = ThreadSafeQueueFactory.create<Layer>()

    override fun pushLayer(layer: Layer) {
        currentLayers.offer(layer)
    }

    override fun popLayer(): Maybe<Layer> {
        return currentLayers.poll()
    }

    override fun removeLayer(layer: Layer) {
        currentLayers.remove(layer)
    }
}
