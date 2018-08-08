package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultLayerable(
        size: Size,
        boundable: Boundable = DefaultBoundable(size))
    : Layerable, Boundable by boundable {

    private val layers = ThreadSafeQueueFactory.create<Layer>()

    override fun pushLayer(layer: Layer) {
        layers.offer(layer)
    }

    override fun popLayer(): Maybe<Layer> {
        return layers.poll()
    }

    override fun removeLayer(layer: Layer) {
        layers.remove(layer)
    }

    override fun getLayers(): List<Layer> {
        return layers.toList()
    }
}
