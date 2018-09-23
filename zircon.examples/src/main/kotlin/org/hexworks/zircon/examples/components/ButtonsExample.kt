package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object ButtonsExample {

    private val theme = ColorThemes.gamebookers()
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
                .wrapWithShadow(true)
                .size(Sizes.create(30, 28))
                .position(Positions.create(29, 1))
                .title("Buttons on panel")
                .build()
        screen.addComponent(panel)

        val simpleBtn = Components.button()
                .text("Button")
                .wrapSides(true)
                .position(Positions.create(1, 3))
        val boxedBtn = Components.button()
                .text("Boxed Button")
                .wrapWithBox(true)
                .wrapSides(false)
                .position(Positions.create(1, 5))
        val tooLongBtn = Components.button()
                .text("Too long name for button")
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .wrapSides(false)
                .position(Positions.create(1, 9))
                .size(Sizes.create(10, 4))
        val overTheTopBtn = Components.button()
                .text("Over the top button")
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE))
                .position(Positions.create(1, 14))
        val halfBlockBtn = Components.button()
                .text("Half block button")
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .position(Positions.create(1, 23))


        screen.addComponent(simpleBtn)
        panel.addComponent(simpleBtn.position(Positions.create(1, 1)).build())

        screen.addComponent(boxedBtn)
        panel.addComponent(boxedBtn.position(Positions.create(1, 3)).build())

        screen.addComponent(tooLongBtn)
        panel.addComponent(tooLongBtn.position(Positions.create(1, 7)).build())

        screen.addComponent(overTheTopBtn)
        panel.addComponent(overTheTopBtn.position(Positions.create(1, 12)).build())

        screen.addComponent(halfBlockBtn)
        panel.addComponent(halfBlockBtn.position(Positions.create(1, 21)).build())

        screen.display()
        screen.applyColorTheme(theme)
    }

}
