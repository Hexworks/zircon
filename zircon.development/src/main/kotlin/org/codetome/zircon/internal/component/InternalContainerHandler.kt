package org.codetome.zircon.internal.component

import org.codetome.zircon.api.component.ContainerHandler
import org.codetome.zircon.api.graphics.Layer

/**
 * Internal API for a [ContainerHandler].
 */
interface InternalContainerHandler : ContainerHandler {

    /**
     * Tells whether this [InternalContainerHandler] is active or not.
     */
    fun isActive(): Boolean

    /**
     * Activates this [InternalContainerHandler]. It will (re) start listening to
     * its related events.
     */
    fun activate()

    /**
     * Deactivates this [InternalContainerHandler]. It will no longer act on
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
