package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.data.LayerState

interface InternalLayerable : Layerable {

    fun fetchLayerStates(): Sequence<LayerState>

    fun removeLayer(layer: Layer): Layer
}
