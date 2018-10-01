package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object PanelsExample {

    private val theme = ColorThemes.techLight()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(1, 1)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(1, 8)))

        screen.addComponent(Components.panel()
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(1, 15)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .withBoxType(BoxType.DOUBLE)
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(1, 22)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .withBoxType(BoxType.BASIC)
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(21, 1)))

        screen.addComponent(Components.panel()
                .wrapWithBox(true)
                .withTitle("Qux")
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(21, 8)))

        screen.addComponent(Components.panel()
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .withSize(Sizes.create(18, 5))
                .withPosition(Positions.create(21, 15)))

        screen.addComponent(Components.panel()
                .withSize(Sizes.create(18, 5))
                .withTitle("Wombat")
                .wrapWithBox(true)
                .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
                .withPosition(Positions.create(21, 22)))

        screen.addComponent(Components.panel()
                .withSize(Sizes.create(18, 5))
                .wrapWithBox(true)
                .withBoxType(BoxType.LEFT_RIGHT_DOUBLE)
                .withPosition(Positions.create(41, 1)))

        val panel = Components.panel()
                .withSize(Sizes.create(18, 19))
                .wrapWithBox(true)
                .withTitle("Parent")
                .withPosition(Positions.create(41, 8))
                .build()

        screen.addComponent(panel)

        val nested0 = Components.panel()
                .withSize(Sizes.create(14, 15))
                .withPosition(Positions.create(1, 1))
                .wrapWithBox(true)
                .withTitle("Nested 0")
                .withBoxType(BoxType.DOUBLE)
                .build()

        val nested1 = Components.panel()
                .withSize(Sizes.create(10, 11))
                .withPosition(Positions.create(1, 1))
                .wrapWithBox(true)
                .withTitle("Nested 1")
                .withBoxType(BoxType.DOUBLE)
                .build()

        panel.addComponent(nested0)
        nested0.addComponent(nested1)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
