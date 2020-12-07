package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer

interface RootContainer : InternalContainer {

    // the Root Container is always attached
    override val isAttached: Boolean
        get() = true

    /**
     * Holds the component tree rooted at this [RootContainer] flattened into an [ObservableList].
     * Note that [componentTree] also contains [root] as the first element.
     */
    val componentTree: ObservableList<InternalComponent>

    fun calculatePathTo(component: InternalComponent): List<InternalComponent>

    fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent>

    override fun addComponent(builder: Builder<Component>): AttachedComponent =
        addComponent(builder.build())

    override fun addComponents(vararg components: Component): List<AttachedComponent> =
        components.map(::addComponent)

    override fun addComponents(vararg components: Builder<Component>): List<AttachedComponent> =
        components.map(::addComponent)

}
