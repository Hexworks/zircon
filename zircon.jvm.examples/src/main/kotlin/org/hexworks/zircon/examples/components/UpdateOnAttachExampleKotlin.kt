package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.extensions.toScreen

class UpdateOnAttachExampleKotlin {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val screen = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                    .withSize(60, 30)
                    .withDefaultTileset(CP437TilesetResources.taffer20x20())
                    .build()
            ).toScreen()

            val box = Components.vbox()
                .withSpacing(1)
                .withAlignment(ComponentAlignments.alignmentWithin(screen, CENTER))
                .withPreferredSize(20, 10)
                .build().apply {
                    addComponent(Components.header().withText("Same style"))
                }

            screen.addComponent(box)

            screen.theme = ColorThemes.gamebookers()
            screen.display()

            val label = Components.label()
                .withText("Different style")
                .withColorTheme(ColorThemes.cherryBear())
                .withUpdateOnAttach(false)
                .build()

            box.addComponent(label)
        }
    }
}
