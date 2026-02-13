package org.hexworks.zircon.api.component

/**
 * Decorator that represents a [Component] that is attached to a [ComponentContainer].
 */
interface AttachedComponent : Component {

    /**
     * Detaches the underlying [Component] from the [ComponentContainer].
     * Detaching a [Component] will reset it to its default state so that
     * it can be re-attached later (possibly to a different [ComponentContainer]).
     */
    fun detach(): Component

}