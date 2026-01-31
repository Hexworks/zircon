package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.builder.Builder

/**
 * Represents an object that can contain gui [Component]s and also maintains
 * a [theme] property that's synchronized between its child [Component]s.
 * Note that a [ComponentContainer] won't enforce consistency: the child
 * themes can be changed individually, but they will be overwritten whenever
 * the [ComponentContainer]'s theme changes.
 * @see Component
 * @see ColorThemeOverride
 */
interface ComponentContainer : ColorThemeOverride {

    /**
     * Adds a child [Component] to this [ComponentContainer]. It can either be
     * a leaf component (like a label) or a [Container] which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component): AttachedComponent

    /**
     * Builds a [Component] using the given component [Builder]
     * and adds it to this [ComponentContainer].
     */
    fun addComponent(builder: Builder<Component>): AttachedComponent = addComponent(builder.build())

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: Component): List<AttachedComponent> = components.map(::addComponent)

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: Builder<Component>): List<AttachedComponent> = components.map(::addComponent)

    /**
     * Adds the [Fragment.root] of the given [Fragment] to this [ComponentContainer].
     */
    fun addFragment(fragment: Fragment): AttachedComponent = addComponent(fragment.root)

    /**
     * Adds the [Fragment.root] of the given [Fragment] to this [ComponentContainer].
     */
    fun addFragments(vararg fragments: Fragment): List<AttachedComponent> = fragments.map(::addFragment)

    /**
     * Detaches all child components and returns them.
     */
    fun detachAllComponents(): List<Component>

}
