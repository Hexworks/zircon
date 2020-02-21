package org.hexworks.zircon.internal.behavior.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Layerable.Companion.WRONG_LAYER_TYPE_MSG
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.graphics.DefaultLayerHandle
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

    override fun getLayerAt(level: Int): Maybe<LayerHandle> {
        return Maybe.ofNullable(DefaultLayerHandle(layers[level], this))
    }

    @Synchronized
    override fun addLayer(layer: Layer): LayerHandle {
        layer as? InternalLayer ?: error(WRONG_LAYER_TYPE_MSG)
        layers = layers.add(layer)
        return DefaultLayerHandle(layer, this)
    }

    @Synchronized
    override fun setLayerAt(level: Int, layer: Layer): LayerHandle {
        layer as? InternalLayer ?: error(WRONG_LAYER_TYPE_MSG)
        level.whenValidIndex {
            layers = layers.set(level, layer)
        }
        return DefaultLayerHandle(layer, this)
    }

    @Synchronized
    override fun insertLayerAt(level: Int, layer: Layer): LayerHandle {
        layer as? InternalLayer ?: error(WRONG_LAYER_TYPE_MSG)
        level.whenValidIndex {
            layers = layers.add(level, layer)
        }
        return DefaultLayerHandle(layer, this)
    }

    // DERIVED FUNCTIONS

    @Synchronized
    override fun removeLayer(layer: Layer): Layer {
        layers.indexOfFirst { it.id == layer.id }.whenValidIndex { idx ->
            layers = layers.removeAt(idx)
        }
        return layer
    }

    private fun Int.whenValidIndex(fn: (Int) -> Unit) {
        if (this in 0 until layers.size) {
            fn(this)
        }
    }

}
