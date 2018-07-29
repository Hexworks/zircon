package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.api.behavior.Boundable
import java.util.*

interface Layerable : Boundable {

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun pushLayer(layer: Layer<out Any, out Any>)

    /**
     * Removes and returns the layer which is at the top of the currently present layers
     * (if any).
     */
    fun popLayer(): Optional<Layer<out Any, out Any>>

    /**
     * Removes a [Layer] from the current layers.
     */
    fun removeLayer(layer: Layer<out Any, out Any>)

    /**
     * Returns a list of [Layer]s which are currently present in this [Layerable].
     */
    fun getLayers(): List<Layer<out Any, out Any>>
}
