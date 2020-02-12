package org.hexworks.zircon.api.graphics

interface LayerHandle : Layer {

    /**
     * Removes the underlying [Layer] from its parent.
     */
    fun remove()
}