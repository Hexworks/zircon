package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.util.Maybe
import java.util.*

/**
 * Represents an object which can contain multiple [org.codetome.zircon.api.graphics.Layer]s
 * which are specialized [org.codetome.zircon.api.graphics.TileImage] overlays displayed
 * above the [Layerable] object.
 */
interface Layerable : Boundable {

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun pushLayer(layer: Layer<out Any, out Any>)

    /**
     * Removes and returns the layer which is at the top of the currently present layers
     * (if any).
     */
    fun popLayer(): Maybe<Layer<out Any, out Any>>

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer<out Any, out Any>)

    /**
     * Returns a list of [Layer]s which are currently present in this [Layerable].
     */
    fun getLayers(): List<Layer<out Any, out Any>>
}
