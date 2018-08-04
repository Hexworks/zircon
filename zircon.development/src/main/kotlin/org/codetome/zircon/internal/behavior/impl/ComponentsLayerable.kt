package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultContainer

class ComponentsLayerable(
        private val layers: Layerable,
        private val components: DefaultContainer)
    : Layerable by layers {

    override fun getLayers(): List<Layer> {
        return components.transformToLayers().plus(layers.getLayers())
    }
}
