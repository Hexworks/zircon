package org.hexworks.zircon.api.component.renderer.extensions

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

/**
 * Casts a covariant [ComponentRenderingStrategy]<out [T]> to an invariant [ComponentRenderingStrategy]<[T]>.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Component> ComponentRenderingStrategy<out T>.asInvariant(): ComponentRenderingStrategy<T> =
    this as ComponentRenderingStrategy<T>
