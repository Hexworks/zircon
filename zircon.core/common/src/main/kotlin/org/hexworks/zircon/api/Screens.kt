package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import kotlin.jvm.JvmStatic

object Screens {

    /**
     * Creates a [Screen] for the given `tileGrid`.
     */
    @JvmStatic
    fun createScreenFor(tileGrid: TileGrid): Screen = ScreenBuilder.createScreenFor(tileGrid)
}
