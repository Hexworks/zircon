package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.platform.factory.PersistentListFactory

class DefaultLayerable : Layerable {

    override val layers: List<Layer>
        get() = currentLayers

    private var currentLayers = PersistentListFactory.create<Layer>()

    override fun addLayer(layer: Layer) {
        currentLayers = currentLayers.add(layer)
    }

    override fun removeLayer(layer: Layer) {
        currentLayers = currentLayers.remove(layer)
    }
}
