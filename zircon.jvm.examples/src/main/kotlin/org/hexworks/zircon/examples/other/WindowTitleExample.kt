package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.taffer20x20
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.data.Size.Companion.create

object WindowTitleExample {

    private val GRID_SIZE = create(20, 10)
    private val TILESET = taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {
        startTileGrid(
            newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(GRID_SIZE)
                .withTitle("Some cool title")
                .build()
        )
    }
}