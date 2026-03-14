package org.hexworks.zircon.internal.component.extensions

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.RootContainer

/**
 * Runs [fn] only if this [Component] [isAttachedToRoot] or it **is** a root.
 */
fun InternalComponent.whenConnectedToRoot(fn: (root: RootContainer) -> Unit) {
    if (isAttachedToRoot || this is RootContainer) {
        root?.let(fn)
    }
}

/**
 * Contains `this` component and all of its descendants
 */
val InternalComponent.flattenedTree: Collection<InternalComponent>
    get() = listOf(this) + children.map { it.asInternalComponent() }.flatMap { it.flattenedTree }

/**
 * Tells whether this [Component] is attached to a [org.hexworks.zircon.api.component.Container].
 */
val InternalComponent.isAttached: Boolean
    get() = parent != null

/**
 * Tells whether this [Component] is attached to a [RootContainer].
 */
val InternalComponent.isAttachedToRoot: Boolean
    get() = root != null
