package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.graphics.Layer

/**
 * Internal API for a [ComponentContainer].
 */
interface InternalComponentContainer : ComponentContainer {

    /**
     * Tells whether this [InternalComponentContainer] is active or not.
     */
    fun isActive(): Boolean

    /**
     * Activates this [InternalComponentContainer]. It will (re) start listening to
     * its related events.
     */
    fun activate()

    /**
     * Deactivates this [InternalComponentContainer]. It will no longer act on
     * container-related events.
     */
    fun deactivate()

    /**
     * Creates a list of [Layer]s out of the current components
     * this container is holding. The [Layer]s are ordered from
     * bottom to top to make it easy to render them.
     */
    fun transformComponentsToLayers(): List<Layer>

}
