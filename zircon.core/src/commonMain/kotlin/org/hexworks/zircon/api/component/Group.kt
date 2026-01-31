package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder

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
     * Adds a child [Component] to this [ComponentContainer]. It can either be
     * a leaf component (like a label) or a [Container] which can itself
     * contain components within itself.
     */
    fun addComponent(component: T): AttachedComponent

    /**
     * Builds a [Component] using the given component [Builder]
     * and adds it to this [ComponentContainer].
     */
    fun addComponent(builder: Builder<T>): AttachedComponent = addComponent(builder.build())

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: T): List<AttachedComponent>

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: Builder<T>): List<AttachedComponent> = components.map(::addComponent)

}