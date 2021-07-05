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
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.graphics.Renderable
import kotlin.jvm.Synchronized

class ThreadSafeLayerable(
    initialSize: Size
) : InternalLayerable {

    override val size: Size = initialSize
    override val layers: ListProperty<InternalLayer> = persistentListOf<InternalLayer>().toProperty()
    override val renderables: List<Renderable>
        get() = layers

    private val listeners = mutableMapOf<UUID, Subscription>()

    override fun getLayerAt(level: Int): Maybe<LayerHandle> {
        return Maybe.ofNullable(DefaultLayerHandle(layers[level]))
    }

    override fun getLayerAtOrNull(level: Int): LayerHandle? =
        layers.getOrNull(level)?.let { DefaultLayerHandle(it) }

    @Synchronized
    override fun addLayer(layer: Layer): LayerHandle {
        val internalLayer = layer.asInternalLayer()
        layers.add(internalLayer)
        return DefaultLayerHandle(internalLayer)
    }

    @Synchronized
    override fun setLayerAt(level: Int, layer: Layer): LayerHandle {
        val internalLayer = layer.asInternalLayer()
        if (level.isValidIndex) {
            listeners[internalLayer.id]?.dispose()
            layers.set(level, internalLayer)
        } else error("Can't insert layer $layer at $level")
        return DefaultLayerHandle(internalLayer)
    }

    @Synchronized
    override fun insertLayerAt(level: Int, layer: Layer): LayerHandle {
        val internalLayer = layer.asInternalLayer()
        if (level.isValidIndex) {
            layers.add(level, internalLayer)
        } else error("Can't insert layer $layer at $level")
        return DefaultLayerHandle(internalLayer)
    }

    @Synchronized
    override fun removeLayer(layer: Layer): Boolean {
        var result = false
        layers.indexOfFirst { it.id == layer.id }.whenValidIndex { idx ->
            layers.removeAt(idx)
            listeners.remove(layer.id)?.dispose()
            result = true
        }
        return result
    }

    private fun Int.whenValidIndex(fn: (Int) -> Unit) {
        if (isValidIndex) {
            fn(this)
        }
    }

    private val Int.isValidIndex: Boolean
        get() = this in 0 until layers.size

    private inner class DefaultLayerHandle(
        private val backend: InternalLayer
    ) : LayerHandle, InternalLayer by backend {

        private var attached = true

        @Synchronized
        override fun removeLayer(): Boolean {
            attached = false
            return removeLayer(backend)
        }

        override fun moveByLevel(level: Int): Boolean {
            return if (attached) {
                if (level == 0) return true
                var result = false
                layers.transformValue { oldValue ->
                    var newValue = oldValue
                    newValue.indexOfFirst { it.id == backend.id }.whenValidIndex { oldIdx ->
                        (oldIdx + level).whenValidIndex { newIdx ->
                            newValue = newValue.removeAt(oldIdx)
                            newValue = newValue.add(newIdx, backend)
                            result = true
                        }
                    }
                    newValue
                }
                result
            } else false
        }

    }
}
