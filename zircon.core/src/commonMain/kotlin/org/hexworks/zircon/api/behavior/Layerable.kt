package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Represents an object which can contain multiple [Layer]s which are specialized
 * [TileGraphics] objects which can be displayed above each other within the [Layerable] object.
 * Indexing is done from bottom to top, eg: calling [addLayer] with a [Layerable] which
 * has only one [Layer] (at index `0`) will add the new [Layer] at index `1`.
 * [Layerable] also implements [Clearable]. In this context [clear] can be used to remove
 * all layers.
 */
interface Layerable {

    val layers: Iterable<Layer>

    /**
     * The area this [Layerable] covers. This means that the maximum size for a [Layer] this
     * [Layerable] accepts is [Layerable.size] - [Layer.position] + [Layer.size]
     */
    val size: Size

    /**
     * Returns the [Layer] at the given [level] (if present).
     */
    fun getLayerAt(level: Int): Maybe<LayerHandle>

    /**
     * Adds a layer on top of the currently present layers.
     */
    fun addLayer(layer: Layer): LayerHandle

    /**
     * Inserts the given [Layer] into this [Layerable] at the given [level].
     */
    fun insertLayerAt(level: Int, layer: Layer): LayerHandle

    /**
     * Sets the given [Layer] in this [Layerable] at the given [level].
     */
    fun setLayerAt(level: Int, layer: Layer): LayerHandle

    companion object {

        internal const val WRONG_LAYER_TYPE_MSG = "The supplied Layer does not implement required interface: InternalLayer."
    }
}
