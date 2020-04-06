package org.hexworks.zircon.internal.behavior.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.graphics.InternalLayer
import kotlin.jvm.Synchronized

class ThreadSafeLayerable(initialSize: Size)
    : InternalLayerable {

    override val size: Size = initialSize

    override val layers: ListProperty<InternalLayer> = persistentListOf<InternalLayer>().toProperty()

    private val listeners = mutableMapOf<UUID, Subscription>()

    override fun fetchLayerStates(): Sequence<LayerState> = sequence {
        layers.value.forEach { yield(it.state) }
    }

    override fun getLayerAt(level: Int): Maybe<LayerHandle> {
        return Maybe.ofNullable(DefaultLayerHandle(layers[level]))
    }

    @Synchronized
    override fun addLayer(layer: Layer): LayerHandle {
        val internalLayer = layer.asInternal()
        layers.add(internalLayer)
        return DefaultLayerHandle(internalLayer)
    }

    @Synchronized
    override fun setLayerAt(level: Int, layer: Layer): LayerHandle {
        val internalLayer = layer.asInternal()
        level.whenValidIndex {
            listeners[internalLayer.id]?.dispose()
            layers.set(level, internalLayer)
        }
        // TODO: should we return this if the level wasn't a valid index?
        return DefaultLayerHandle(internalLayer)
    }

    @Synchronized
    override fun insertLayerAt(level: Int, layer: Layer): LayerHandle {
        val internalLayer = layer.asInternal()
        level.whenValidIndex {
            layers.add(level, internalLayer)
        }
        // TODO: should we return this if the level wasn't a valid index?
        return DefaultLayerHandle(internalLayer)
    }

    // DERIVED FUNCTIONS

    @Synchronized
    override fun removeLayer(layer: Layer): Layer {
        layers.indexOfFirst { it.id == layer.id }.whenValidIndex { idx ->
            layers.removeAt(idx)
            listeners.remove(layer.id)?.dispose()
        }
        // TODO: should we return this if the level wasn't a valid index?
        return layer
    }

    private fun Int.whenValidIndex(fn: (Int) -> Unit) {
        if (this in 0 until layers.size) {
            fn(this)
        }
    }

    private inner class DefaultLayerHandle(
            private val backend: InternalLayer
    ) : LayerHandle, Layer by backend {

        override fun removeLayer() = removeLayer(backend)

    }

}
