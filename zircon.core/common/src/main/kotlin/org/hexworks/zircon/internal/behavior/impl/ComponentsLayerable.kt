package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultContainer

class ComponentsLayerable(
        private val layerable: Layerable,
        private val components: InternalComponentContainer)
    : Layerable by layerable {

    override val layers: List<Layer>
        get() = components.toFlattenedLayers().plus(layerable.layers)
}
