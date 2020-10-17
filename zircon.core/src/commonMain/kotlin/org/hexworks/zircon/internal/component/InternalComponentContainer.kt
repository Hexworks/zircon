package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.internal.behavior.RenderableContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.uievent.UIEventDispatcher

/**
 * Internal API for a [ComponentContainer].
 */
interface InternalComponentContainer : ComponentContainer, RenderableContainer, UIEventDispatcher {

    val isActive: ObservableValue<Boolean>

    /**
     * Holds the component tree rooted at this [RootContainer] flattened into an [ObservableList].
     */
    val flattenedTree: Iterable<InternalComponent>

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
}
