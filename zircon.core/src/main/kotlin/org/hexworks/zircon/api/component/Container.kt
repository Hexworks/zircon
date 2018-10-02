package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.util.Maybe

/**
 * A [Container] is a [Component] which can contain other components.
 * Those components will be bounded by this container.
 * You can add other [Container]s to a container but you can't add
 * components to a [Component].
 */
interface Container : Component {

    // TODO: implement ComponentContainer?
    /**
     * The immediate child [Component]s of this [Container].
     */
    val children: List<Component>

    /**
     * Adds a child [Component] to this [Container]. It can either be
     * a leaf component (like a label) or another container which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component)

    /**
     * Adds a child [Component] to this [Container]. It can either be
     * a leaf component (like a label) or another container which can itself
     * contain components within itself.
     */
    fun addComponent(builder: Builder<Component>) = addComponent(builder.build())

    /**
     * Removes the given [Component] from this [Container].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return true if a removal happened
     */
    fun removeComponent(component: Component): Boolean

    // TODO: move these to `InternalContainer`
    fun acceptsFocus() = false

    fun giveFocus(input: Maybe<Input>) = false

    fun takeFocus(input: Maybe<Input>) {}
}
