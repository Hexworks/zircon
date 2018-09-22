package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object TextAreasExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(28, 28))
                .position(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textArea()
                .text("Some text")
                .size(Sizes.create(13, 5))
                .position(Positions.create(2, 2)))
        panel.addComponent(Components.textArea()
                .text("Some text")
                .size(Sizes.create(13, 5))
                .position(Positions.create(2, 2)))

        screen.addComponent(Components.textArea()
                .text("Some other text")
                .boxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(13, 7))
                .position(Positions.create(2, 8)))
        panel.addComponent(Components.textArea()
                .text("Some other text")
                .boxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(13, 7))
                .position(Positions.create(2, 8)))

        screen.display()
        screen.applyColorTheme(theme)
    }

}
