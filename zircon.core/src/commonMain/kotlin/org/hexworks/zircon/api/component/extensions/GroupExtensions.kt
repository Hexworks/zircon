package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.Group

/**
 * Builds a [Component] using the given component [Builder]
 * and adds it to this [ComponentContainer].
 */
fun <T : Component> Group<T>.addComponent(builder: Builder<T>): AttachedComponent = addComponent(builder.build())

/**
 * Adds the given [Component]s to this [ComponentContainer].
 * @see addComponent
 */
fun <T : Component> Group<T>.addComponents(vararg components: Builder<T>): List<AttachedComponent> =
    components.map(::addComponent)
