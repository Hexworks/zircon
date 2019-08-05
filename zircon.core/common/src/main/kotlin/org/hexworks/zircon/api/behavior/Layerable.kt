package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer

/**
 * Represents an object which can contain multiple [Layer]s
 * which are specialized [DrawSurface]s which can be displayed
 * above each other within the [Layerable] object.
 */
// TODO: sanetize the api!
interface Layerable {

    /**
     * The list of [Layer]s which are currently present in this [Layerable].
     */
    val layers: List<Layer>

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun addLayer(layer: Layer)

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer)
}
