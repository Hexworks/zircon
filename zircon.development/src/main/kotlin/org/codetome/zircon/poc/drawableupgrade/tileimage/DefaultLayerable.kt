package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.poc.drawableupgrade.drawables.Layer
import org.codetome.zircon.poc.drawableupgrade.drawables.Layerable
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class DefaultLayerable<S: Any> : Layerable<S> {

    private val layers = LinkedBlockingQueue<Layer<out Any, S>>()

    override fun pushLayer(layer: Layer<out Any, S>) {
        layers.offer(layer)
    }

    override fun popLayer(): Optional<Layer<out Any, S>> {
        return Optional.ofNullable(layers.poll())
    }

    override fun removeLayer(layer: Layer<out Any, S>) {
        layers.remove(layer)
    }

    override fun getLayers(): List<Layer<out Any, S>> {
        return layers.toList()
    }

    override fun drainLayers(): List<Layer<out Any, S>> {
        val result = mutableListOf<Layer<out Any, S>>()
        layers.drainTo(result)
        return result
    }
}
