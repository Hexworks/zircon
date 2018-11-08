package org.hexworks.zircon.api.builder.screen

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen

class ScreenBuilder {

    companion object {

        fun createScreenFor(tileGrid: TileGrid): Screen {
            return TileGridScreen(
                    tileGrid = tileGrid as InternalTileGrid)
        }
    }
}
