package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Clearable

/**
 * A [Container] is a [Component] which can contain other components.
 * Those components will be bounded by this container.
 * You can add other [Container]s to a container but you can't add
 * components to a [Component].
 */
interface Container : Clearable, ComponentContainer, Component {

    /**
     * The immediate child [Component]s of this [Container].
     */
    val children: Iterable<Component>

}
