package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.graphics.Layer
import java.util.*

/**
 * Represents an object which can contain multiple [org.codetome.zircon.api.graphics.Layer]s
 * which are specialized [org.codetome.zircon.api.graphics.TextImage] overlays displayed
 * above the [Layerable] object.
 */
interface Layerable : Boundable {

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun addLayer(layer: Layer)

    /**
     * Removes and returns the layer which is at the top of the currently present layers
     * (if any).
     */
    fun popLayer(): Optional<Layer>

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer)

    /**
     * Returns a list of [Layer]s which are currently present in this [Layerable].
     */
    fun getLayers(): List<Layer>

    /**
     * Removes all [Layer]s from this [Layerable] and returns them.
     */
    fun drainLayers(): List<Layer>
}