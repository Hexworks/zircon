package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.extensions.toScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid().toScreen()

        val panel = Components.panel()
            .build()
    }

}

