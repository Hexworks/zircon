package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.Group

interface InternalGroup<T : Component> : Group<T> {

    /**
     * Removes the given [Component] from this [ComponentContainer].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return `true` if change happened, `false` if not
     */
    fun removeComponent(component: Component)
}