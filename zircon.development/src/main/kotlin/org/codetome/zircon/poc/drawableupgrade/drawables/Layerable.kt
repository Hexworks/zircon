package org.codetome.zircon.poc.drawableupgrade.drawables

import java.util.*

interface Layerable<S: Any> {

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun pushLayer(layer: Layer<out Any, S>)

    /**
     * Removes and returns the layer which is at the top of the currently present layers
     * (if any).
     */
    fun popLayer(): Optional<Layer<out Any, S>>

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer<out Any, S>)

    /**
     * Returns a list of [Layer]s which are currently present in this [Layerable].
     */
    fun getLayers(): List<Layer<out Any, S>>

    /**
     * Removes all [Layer]s from this [Layerable] and returns them.
     */
    fun drainLayers(): List<Layer<out Any, S>>
}
