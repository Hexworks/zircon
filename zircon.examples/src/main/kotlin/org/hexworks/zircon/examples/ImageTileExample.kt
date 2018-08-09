package org.hexworks.zircon.examples

import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.resource.CP437TilesetResource

object ImageTileExample {

    private val TILESET = CP437TilesetResource.WANDERLUST_16X16

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigBuilder.newBuilder()
                .defaultTileset(TILESET)
                .defaultSize(Sizes.create(80, 50))
                .debugMode(true)
                .build())


    }

}
