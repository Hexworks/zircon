package org.hexworks.zircon.api.component

/**
 * Decorator that represents a [Component] which is attached to a [ComponentContainer].
 */
interface AttachedComponent : Component {

    /**
     * Detaches the underlying [Component] from the [ComponentContainer] it is
     * attached to and returns the [Component].
     */
    fun detach(): Component

}