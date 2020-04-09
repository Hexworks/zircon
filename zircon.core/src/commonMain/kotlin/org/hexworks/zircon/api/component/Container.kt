package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.InternalContainer

/**
 * A [Container] is a [Component] which can contain other components.
 * Those components will be bounded by this container.
 * You can add other [Container]s to a container but you can't add
 * components to a [Component].
 */
interface Container : Component, ComponentContainer {

    /**
     * The immediate child [Component]s of this [Container].
     */
    val children: Iterable<Component>

    override fun asInternalComponent(): InternalContainer
}
