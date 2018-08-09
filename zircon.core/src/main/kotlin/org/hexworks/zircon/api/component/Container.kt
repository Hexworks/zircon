package org.hexworks.zircon.api.component

/**
 * A [Container] is a [Component] which can contain other components.
 * Those components will be bounded by this container.
 * You can add other [Container]s to a container but you can't add
 * components to a [Component].
 */
interface Container : Component {

    /**
     * Adds a child [Component] to this [Container]. It can either be
     * a leaf component (like a label) or another container which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component)

    /**
     * Removes the given [Component] from this [Container].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return true if a removal happened
     */
    fun removeComponent(component: Component): Boolean
}
