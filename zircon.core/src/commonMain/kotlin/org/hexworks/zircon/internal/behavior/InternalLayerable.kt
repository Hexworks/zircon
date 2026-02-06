package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.graphics.InternalLayer

interface InternalLayerable : Layerable, RenderableContainer {

    override val layers: ObservableList<out InternalLayer>

    fun removeLayer(layer: Layer): Boolean
}
