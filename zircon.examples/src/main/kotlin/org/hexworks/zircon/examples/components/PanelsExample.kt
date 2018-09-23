package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object PanelsExample {

    private val theme = ColorThemes.gamebookers()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 1)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 8)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 15)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .boxType(BoxType.DOUBLE)
                .size(Sizes.create(18, 5))
                .position(Positions.create(1, 22)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .boxType(BoxType.BASIC)
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 1)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .title("Qux")
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 8)))

        screen.addComponent(Components.panel()
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .size(Sizes.create(18, 5))
                .position(Positions.create(21, 15)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .title("Wombat")
                .wrapWithBox(true)
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .position(Positions.create(21, 22)))

        screen.addComponent(Components.panel()
                .size(Sizes.create(18, 5))
                .wrapWithBox(true)
                .boxType(BoxType.LEFT_RIGHT_DOUBLE)
                .position(Positions.create(41, 1)))

        val panel = Components.panel()
                .size(Sizes.create(18, 19))
                .wrapWithBox(true)
                .title("Parent")
                .position(Positions.create(41, 8))
                .build()

        screen.addComponent(panel)

        val nested0 = Components.panel()
                .size(Sizes.create(14, 15))
                .position(Positions.create(1, 1))
                .wrapWithBox(true)
                .title("Nested 0")
                .boxType(BoxType.DOUBLE)
                .build()

        val nested1 = Components.panel()
                .size(Sizes.create(10, 11))
                .position(Positions.create(1, 1))
                .wrapWithBox(true)
                .title("Nested 1")
                .boxType(BoxType.DOUBLE)
                .build()

        panel.addComponent(nested0)
        nested0.addComponent(nested1)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
