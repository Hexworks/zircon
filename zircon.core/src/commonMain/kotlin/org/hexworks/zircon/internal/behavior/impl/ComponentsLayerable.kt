package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer

// TODO: refactor this to take layerables in any order
class ComponentsLayerable(
        private val componentContainer: InternalComponentContainer,
        private val layerable: InternalLayerable
) : InternalLayerable by layerable {

    override fun fetchLayerStates() = sequence {
        componentContainer.fetchLayerStates().forEach { yield(it) }
        layerable.fetchLayerStates().forEach { yield(it) }
    }
}