package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.poc.drawableupgrade.drawables.Layer
import org.codetome.zircon.poc.drawableupgrade.drawables.Layerable
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

    override fun popLayer(): Optional<Layer<out Any, out Any>> {
        return Optional.ofNullable(layers.poll())
    }

    override fun removeLayer(layer: Layer<out Any, out Any>) {
        layers.remove(layer)
    }

    override fun getLayers(): List<Layer<out Any, out Any>> {
        return layers.toList()
    }
}
