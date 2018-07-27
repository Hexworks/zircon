package org.codetome.zircon.api.builder.screen

import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.screen.TileGridScreen
import org.codetome.zircon.internal.grid.InternalTileGrid

class ScreenBuilder {

    companion object {

        fun createScreenFor(tileGrid: TileGrid): Screen {
            return TileGridScreen(
                    terminal = tileGrid as InternalTileGrid)
        }
    }
}
