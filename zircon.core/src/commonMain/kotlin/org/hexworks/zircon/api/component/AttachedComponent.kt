package org.hexworks.zircon.api.component

/**
 * Represents a [Component] which is attached to a [ComponentContainer].
 */
interface AttachedComponent : Component {

    /**
     * Detaches the underlying [Component] from the [ComponentContainer] it is
     * attached and returns the underlying [Component].
     */
    fun detach(): Component

}