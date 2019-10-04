package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.internal.component.InternalComponentContainer
import kotlin.jvm.Synchronized

class ComponentsLayerable(
        private val componentContainer: InternalComponentContainer,
        private val layerable: Layerable) : Layerable by layerable {

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = componentContainer.layerStates.plus(layerable.layerStates)
}