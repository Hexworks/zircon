package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.listener.MouseListener
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource

object KotlinPlayground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437TilesetResource.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SIZE)
                .debugMode(true)
                .build())

    }

}
