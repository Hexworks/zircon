package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.graphics.Layer

/**
 * Represents an object which can contain multiple [org.hexworks.zircon.api.graphics.Layer]s
 * which are specialized [org.hexworks.zircon.api.graphics.DrawSurface] overlays displayed
 * above the [Layerable] object.
 */
interface Layerable {

    /**
     * The list of [Layer]s which are currently present in this [Layerable].
     */
    val layers: List<Layer>

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
