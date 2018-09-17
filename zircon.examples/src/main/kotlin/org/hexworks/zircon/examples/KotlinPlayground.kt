package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BorderDecorationRenderer

object KotlinPlayground {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(23, 24))
                .defaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.button()
                .decorationRenderers(BorderDecorationRenderer(Borders.newBuilder().build()))
                .text("Foobar"))

    }


}
