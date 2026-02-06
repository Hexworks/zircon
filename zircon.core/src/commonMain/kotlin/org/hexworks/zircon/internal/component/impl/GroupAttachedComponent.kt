package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.component.InternalGroup

class GroupAttachedComponent(
    private val component: Component,
    private val group: InternalGroup<out Component>
) : AttachedComponent, Component by component {

    override fun detach(): Component {
        group.removeComponent(component)
        return component
    }
}
