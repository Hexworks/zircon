package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.Fragment

/**
 * Builds a [Component] using the given component [Builder]
 * and adds it to this [ComponentContainer].
 */
fun ComponentContainer.addComponent(builder: Builder<Component>): AttachedComponent = addComponent(builder.build())

/**
 * Adds the given [Component]s to this [ComponentContainer].
 * @see addComponent
 */
fun ComponentContainer.addComponents(vararg components: Component): List<AttachedComponent> =
    components.map(::addComponent)

/**
 * Adds the given [Component]s to this [ComponentContainer].
 * @see addComponent
 */
fun ComponentContainer.addComponents(vararg components: Builder<Component>): List<AttachedComponent> =
    components.map(::addComponent)

/**
 * Adds the [Fragment.root] of the given [Fragment] to this [ComponentContainer].
 */
fun ComponentContainer.addFragment(fragment: Fragment): AttachedComponent = addComponent(fragment.root)

/**
 * Adds the [Fragment.root] of the given [Fragment] to this [ComponentContainer].
 */
fun ComponentContainer.addFragments(vararg fragments: Fragment): List<AttachedComponent> = fragments.map(::addFragment)
