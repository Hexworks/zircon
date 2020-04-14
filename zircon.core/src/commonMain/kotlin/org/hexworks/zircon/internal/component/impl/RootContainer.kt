package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.data.LayerState

interface RootContainer : InternalContainer {

    // the Root Container is always attached
    override val isAttached: Boolean
        get() = true

    val componentTree: PersistentList<InternalComponent>

    fun calculatePathTo(component: InternalComponent): List<InternalComponent>

    fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent>

    fun fetchLayerStates(): Sequence<LayerState>

    override fun addComponent(builder: Builder<Component>): AttachedComponent =
            addComponent(builder.build())

    override fun addComponents(vararg components: Component): List<AttachedComponent> =
            components.map(::addComponent)

    override fun addComponents(vararg components: Builder<Component>): List<AttachedComponent> =
            components.map(::addComponent)

}
