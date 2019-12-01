package org.hexworks.zircon.api.builder.screen

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen
import kotlin.jvm.JvmStatic

object ScreenBuilder {

    /**
     * Wraps the given [TileGrid] in a [Screen].
     */
    @JvmStatic
    fun createScreenFor(tileGrid: TileGrid): Screen {
        require(tileGrid is InternalTileGrid) {
            "The supplied TileGrid is not an instance of InternalTileGrid."
        }
        return TileGridScreen(tileGrid)
    }
}
