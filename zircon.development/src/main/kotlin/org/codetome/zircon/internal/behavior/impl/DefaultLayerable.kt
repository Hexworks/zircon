package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.util.Maybe
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class DefaultLayerable(
        size: Size,
        boundable: Boundable = DefaultBoundable(size))
    : Layerable, Boundable by boundable {

    private val layers = LinkedBlockingQueue<Layer>()

    override fun pushLayer(layer: Layer) {
        layers.offer(layer)
    }

    override fun popLayer(): Maybe<Layer> {
        return Maybe.ofNullable(layers.poll())
    }

    override fun removeLayer(layer: Layer) {
        layers.remove(layer)
    }

    override fun getLayers(): List<Layer> {
        return layers.toList()
    }
}
