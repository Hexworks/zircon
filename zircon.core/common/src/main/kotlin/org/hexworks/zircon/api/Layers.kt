package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

object Layers {

    /**
     * Returns the default filler which is an empty [Tile].
     */
    @JvmStatic
    fun defaultFiller() = Tile.empty()

    /**
     * Creates a new [LayerBuilder].
     */
    @JvmStatic
    fun newBuilder() = LayerBuilder()
}
