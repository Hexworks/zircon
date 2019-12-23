package org.hexworks.zircon.api.component

/**
 * A [Group] is a logical grouping of [Component]s. It can be used to
 * change their properties together even if the underlying [Component]s
 * are not part of the same tree (eg: they are siblings, or either in completely
 * different component trees). **Note that** the [Component]s in this [Group]
 * can still be changed individually: the [Group] won't enforce consistency
 * between [Group] elements.
 */
interface Group<T : Component> : ComponentProperties {

    /**
     * Adds the given [component] to this [Group]. After the addition is complete
     * the [ComponentProperties] of the given [component] will be updated whenever this
     * [Group]'s properties are updated. Has no effect if the [component] is already in this [Group].
     */
    fun add(component: T)

    fun addAll(vararg components: T)

    /**
     * Removes the given [component] from this [Group]. After the removal the given
     * [component] won't be updated anymore when the properties of this [Group] change.
     * Note that this function has no effect if the given [component] was not part of this group.
     */
    fun remove(component: T)

    fun removeAll(vararg components: T)
}