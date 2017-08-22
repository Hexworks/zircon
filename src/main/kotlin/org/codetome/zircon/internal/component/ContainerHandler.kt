package org.codetome.zircon.internal.component

import org.codetome.zircon.api.component.Container
import org.codetome.zircon.api.graphics.TextImage

/**
 * Represents an object which can holds gui [org.codetome.zircon.component.Component]s.
 * **Note that** a [ContainerHandler] **will always** hold a [Container]
 * which will have the [org.codetome.zircon.Size] of its parent.
 * @see org.codetome.zircon.component.Component for more info
 */
interface ContainerHandler {

    /**
     * Returns the [Container] this [ContainerHandler] is holding.
     */
    fun getContainer(): Container

    /**
     * Tells whether this [ContainerHandler] is active or not.
     */
    fun isActive(): Boolean

    /**
     * Activates this [ContainerHandler]. It will (re) start listening to
     * its related events.
     */
    fun activate()

    /**
     * Deactivates this [ContainerHandler]. It will no longer act on
     * container-related events.
     */
    fun deactivate()

    /**
     * Creates a [TextImage] of the current contents of the [Container]
     * which this [ContainerHandler] is holding.
     */
    fun drawComponentsToImage(): TextImage

}