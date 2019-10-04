package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.platform.factory.PersistentListFactory
import kotlin.jvm.Synchronized

// TODO: test this thoroughly
class ThreadSafeLayerable(initialSize: Size)
    : Layerable {

    override val size: Size = initialSize

    override var layers = PersistentListFactory.create<Layer>()

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = layers.map { it.state }

    override fun getLayerAt(index: Int): Maybe<Layer> {
        return Maybe.ofNullable(layers[index])
    }

    @Synchronized
    override fun addLayer(layer: Layer) {
        layers = layers.add(layer)
    }

    @Synchronized
    override fun setLayerAt(index: Int, layer: Layer) {
        index.whenValidIndex {
            layers = layers.set(index, layer)
        }
    }

    @Synchronized
    override fun insertLayerAt(index: Int, layer: Layer) {
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
}
