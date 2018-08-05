package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.config.RuntimeConfig

object Layers {

    @JvmStatic
    fun defaultFiller() = Tile.empty()

    @JvmStatic
    fun newBuilder() = LayerBuilder()
}
