package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.event.ZirconScope

interface RootContainer : InternalContainer {

    // the Root Container is always attached
    override val isAttached: Boolean
        get() = true

    /** The grid's event bus; the root container is always attached, so it is always known. */
    val eventBus: EventBus

    val eventScope: ZirconScope

    /**
     * Holds the component tree rooted at this [RootContainer] flattened into an [ObservableList].
     * Note that [componentTree] also contains [root] as the first element.
     */
    val componentTree: ObservableList<InternalComponent>

    fun calculatePathTo(component: InternalComponent): List<InternalComponent>

    fun fetchComponentByPositionOrNull(absolutePosition: Position): InternalComponent?

}
