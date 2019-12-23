package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.builder.Builder
import kotlin.jvm.Synchronized

/**
 * Represents an object which can hold gui [Component]s.
 * @see Component for more info
 */
interface ComponentContainer : Themeable {

    /**
     * Adds a child [Component] to this [ComponentContainer]. It can either be
     * a leaf component (like a label) or a [Container] which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component)

    /**
     * Builds a [Component] using the given component [Builder]
     * and adds it to this [ComponentContainer].
     */
    fun addComponent(builder: Builder<Component>) = addComponent(builder.build())

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: Component) = components.forEach(::addComponent)

    /**
     * Adds the given [Component]s to this [ComponentContainer].
     * @see addComponent
     */
    fun addComponents(vararg components: Builder<Component>) = components.forEach(::addComponent)


    /**
     * Adds the [Fragment.root] of the given [Fragment] to this [ComponentContainer].
     */
    fun addFragment(fragment: Fragment) = addComponent(fragment.root)

    /**
     * Removes the given [Component] from this [ComponentContainer].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return `true` if change happened, `false` if not
     */
    fun removeComponent(component: Component): Boolean

}
