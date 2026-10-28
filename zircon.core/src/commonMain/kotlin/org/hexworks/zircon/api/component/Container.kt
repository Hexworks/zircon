package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.InternalContainer

/**
 * A [Container] is a [Component] that can contain other components.
 * Those components will be within the bounds of this container.
 * You can add other [Container]s to a container, but you can't add
 * components to a [Component].
 *
 * @see ComponentContainer
 * @see Component
 */
public interface Container : Component, ComponentContainer {

    /**
     * The immediate child [Component]s of this [Container].
     */
    public val children: Iterable<Component>

}
