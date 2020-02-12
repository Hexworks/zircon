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

    override fun getLayerAt(index: Int): Maybe<Layer> {
        return Maybe.ofNullable(layers[index])
    }

    @Synchronized
    override fun addLayer(layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        layers = layers.add(layer)
    }

    @Synchronized
    override fun setLayerAt(index: Int, layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        index.whenValidIndex {
            layers = layers.set(index, layer)
        }
    }

    @Synchronized
    override fun insertLayerAt(index: Int, layer: Layer) {
        layer as? InternalLayer ?: error(ERROR_MSG)
        index.whenValidIndex {
            layers = layers.add(index, layer)
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
    override fun insertLayersAt(index: Int, layers: Collection<Layer>) {
        layers.forEachIndexed { idx, layer ->
            insertLayerAt(index + idx, layer)
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

    private fun Int.whenValidIndex(fn: (Int) -> Unit) {
        if (this in 0 until layers.size) {
            fn(this)
        }
    }

    companion object {
        private const val ERROR_MSG = "The supplied component does not implement required interface: InternalLayer."
    }
}
