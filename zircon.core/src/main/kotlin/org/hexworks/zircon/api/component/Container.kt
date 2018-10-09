package org.hexworks.zircon.api.component

/**
 * A [Container] is a [Component] which can contain other components.
 * Those components will be bounded by this container.
 * You can add other [Container]s to a container but you can't add
 * components to a [Component].
 */
interface Container : ComponentContainer, Component {

    /**
     * The immediate child [Component]s of this [Container].
     */
    val children: List<Component>

    /**
     * Removes all [Component]s from this [Container].
     * @return true if at least one component was removed
     */
    fun detachAllComponents(): Boolean

}
