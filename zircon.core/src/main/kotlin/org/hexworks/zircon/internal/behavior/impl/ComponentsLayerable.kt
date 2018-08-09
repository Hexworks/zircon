package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.component.impl.DefaultContainer

class ComponentsLayerable(
        private val layers: Layerable,
        private val components: DefaultContainer)
    : Layerable by layers {

    override fun getLayers(): List<Layer> {
        return components.transformToLayers().plus(layers.getLayers())
    }
}
