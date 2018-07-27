package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.screen.ScreenBuilder
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.grid.TileGrid

object Screens {

    @JvmStatic
    fun createScreenFor(tileGrid: TileGrid): Screen = ScreenBuilder.createScreenFor(tileGrid)
}
