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

    private val layers = LinkedBlockingQueue<Layer<out Any, out Any>>()

    override fun pushLayer(layer: Layer<out Any, out Any>) {
        layers.offer(layer)
    }

    override fun popLayer(): Maybe<Layer<out Any, out Any>> {
        return Maybe.ofNullable(layers.poll())
    }

    override fun removeLayer(layer: Layer<out Any, out Any>) {
        layers.remove(layer)
    }

    override fun getLayers(): List<Layer<out Any, out Any>> {
        return layers.toList()
    }
}
