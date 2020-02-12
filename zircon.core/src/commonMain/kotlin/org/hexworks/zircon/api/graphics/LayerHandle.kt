package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Layerable

/**
 * Represents a [Layer] which is attached to a [Layerable] objects and
 * has additional functionality related to the [Layerable] such as [remove].
 */
interface LayerHandle : Layer {

    /**
     * Removes the underlying [Layer] from its parent [Layerable].
     */
    fun remove(): Layer
}