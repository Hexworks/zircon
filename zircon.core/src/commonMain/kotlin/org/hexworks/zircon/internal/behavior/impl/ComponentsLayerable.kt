package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import kotlin.jvm.Synchronized

class ComponentsLayerable(
        private val componentContainer: InternalComponentContainer,
        private val layerable: InternalLayerable) : InternalLayerable by layerable {

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = componentContainer.layerStates.plus(layerable.layerStates)
}