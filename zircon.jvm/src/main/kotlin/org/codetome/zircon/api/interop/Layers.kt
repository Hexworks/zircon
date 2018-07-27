package org.codetome.zircon.api.interop

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.internal.tileset.impl.FontSettings

object Layers {

    @JvmStatic
    fun defaultFont() = FontSettings.NO_FONT

    @JvmStatic
    fun defaultSize() = Size.one()

    @JvmStatic
    fun defaultFiller() = Tile.empty()

    @JvmStatic
    fun newBuilder() = LayerBuilder()
}
