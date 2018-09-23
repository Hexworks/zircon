package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

object KotlinPlayground {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(40, 25))
                .defaultTileset(CP437TilesetResources.rexPaint20x20())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .title("Panel")
                .size(Size.create(32, 16))
                .position(Position.create(1, 1))
                .build()
        val panelHeader = HeaderBuilder.newBuilder()
                .position(Positions.create(1, 0))
                .text("Header")
                .build()

        val innerPanelHeader = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header2")
                .build()
        val innerPanel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .title("Panel2")
                .size(Size.create(16, 10))
                .position(Positions.create(1, 2))
                .build()

        innerPanel.addComponent(innerPanelHeader)
        panel.addComponent(panelHeader)

        panel.addComponent(innerPanel)
        screen.addComponent(panel)

        screen.display()

    }


}
