package org.hexworks.zircon.internal.component

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.uievent.UIEventDispatcher

/**
 * Internal API for a [ComponentContainer].
 */
interface InternalComponentContainer : ComponentContainer, UIEventDispatcher {

    val isActive: ObservableValue<Boolean>

    /**
     * Activates this [InternalComponentContainer]. It will (re) start listening to
     * its related events.
     */
    fun activate()

    /**
     * Deactivates this [InternalComponentContainer]. It will no longer act on
     * container-related events.
     */
    fun deactivate()

    fun fetchLayerStates(): Sequence<LayerState>

}
