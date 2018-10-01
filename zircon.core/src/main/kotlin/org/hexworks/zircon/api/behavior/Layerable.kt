package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.util.Maybe

/**
 * Represents an object which can contain multiple [org.hexworks.zircon.api.graphics.Layer]s
 * which are specialized [DrawSurface] overlays displayed
 * above the [Layerable] object.
 */
interface Layerable : Boundable {

    /**
     * Returns a list of [Layer]s which are currently present in this [Layerable].
     */
    fun layers(): List<Layer>

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun pushLayer(layer: Layer)

    /**
     * Removes and returns the layer which is at the top of the currently present layers
     * (if any).
     */
    fun popLayer(): Maybe<Layer>

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer)
}
