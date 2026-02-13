package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.Group

interface InternalGroup<T : Component> : Group<T> {

    /**
     * Removes the given [Component] from this [InternalGroup] (if present)
     */
    fun removeComponent(component: Component)
}