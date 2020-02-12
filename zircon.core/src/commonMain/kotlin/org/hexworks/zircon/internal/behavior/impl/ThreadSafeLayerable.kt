package org.hexworks.zircon.internal.behavior.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.graphics.InternalLayer
import kotlin.jvm.Synchronized

// TODO: test this thoroughly
class ThreadSafeLayerable(initialSize: Size)
    : InternalLayerable {

    override val size: Size = initialSize

    override var layers = persistentListOf<InternalLayer>()

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = layers.map { it.state }

    override fun getLayerAt(level: Int): Maybe<Layer> {
        return Maybe.ofNullable(layers[level])
    }

    @Synchronized
    override fun addLayer(layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        layers = layers.add(layer)
    }

    @Synchronized
    override fun setLayerAt(level: Int, layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        level.whenValidIndex {
            layers = layers.set(level, layer)
        }
    }

    @Synchronized
    override fun insertLayerAt(level: Int, layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        level.whenValidIndex {
            layers = layers.add(level, layer)
        }
    }


    @Synchronized
    override fun removeLayerAt(index: Int) {
        index.whenValidIndex {
            layers = layers.removeAt(index)
        }
    }

    // DERIVED FUNCTIONS

    @Synchronized
    override fun removeLayer(layer: Layer) {
        layers.indexOfFirst { it.id == layer.id }.whenValidIndex { idx ->
            removeLayerAt(idx)
        }
    }

    @Synchronized
    override fun removeLayers(layers: Collection<Layer>) {
        layers.forEach(::removeLayer)
    }

    @Synchronized
    override fun removeAllLayers() {
        removeLayers(layers)
    }

    @Synchronized
    override fun clear() {
        removeLayers(layers)
    }

    private fun Int.whenValidIndex(fn: (Int) -> Unit) {
        if (this in 0 until layers.size) {
            fn(this)
        }
    }

    companion object {
        private const val ERROR_MSG = "The supplied component does not implement required interface: InternalLayer."
    }
}
