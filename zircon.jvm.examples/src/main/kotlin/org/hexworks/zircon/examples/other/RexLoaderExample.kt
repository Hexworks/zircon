package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.taffer20x20
import org.hexworks.zircon.api.CP437TilesetResources.yobbo20x20
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.resource.REXPaintResources.loadREXFile
import org.hexworks.zircon.api.screen.Screen.Companion.create

object RexLoaderExample {
    private const val TERMINAL_WIDTH = 16
    private const val TERMINAL_HEIGHT = 16
    private val TILESET = yobbo20x20()
    private val SIZE = create(TERMINAL_WIDTH, TERMINAL_HEIGHT)
    private val RESOURCE = RexLoaderExample::class.java.getResourceAsStream("/rex_files/cp437_table.xp")

    @JvmStatic
    fun main(args: Array<String>) {
        val rex = loadREXFile(RESOURCE)
        val tileGrid = startTileGrid(
            newBuilder()
                .withDefaultTileset(taffer20x20())
                .withSize(SIZE)
                .withDebugMode(true)
                .build()
        )
        val screen = create(tileGrid)
        val layers = rex.toLayerList(TILESET)
        for (layer in layers) {
            screen.addLayer(layer)
        }
        screen.display()
    }
}