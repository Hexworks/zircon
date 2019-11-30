package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.HalfBlockDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.ShadowDecorationRenderer

object ButtonsTest {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(Size.create(30, 28))
                .withPosition(Position.create(29, 1))
                .build()
        screen.addComponent(panel)

        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                .withPosition(Position.create(1, 3))
        val boxedBtn = Components.button()
                .withText("Boxed Button")
                .withDecorations(box())
                .withPosition(Position.create(1, 5))
        val tooLongBtn = Components.button()
                .withText("Too long name for button")
                .withDecorations(box(), shadow())
                .withPosition(Position.create(1, 9))
                .withSize(Size.create(10, 4))
        val overTheTopBtn = Components.button()
                .withText("Over the top button")
                .withDecorations(
                        HalfBlockDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        ShadowDecorationRenderer())
                .withPosition(Position.create(1, 14))
        val halfBlockBtn = Components.button()
                .withText("Half block button")
                .withDecorations(
                        HalfBlockDecorationRenderer(),
                        ShadowDecorationRenderer())
                .withPosition(Position.create(1, 23))


        screen.addComponent(simpleBtn)
        panel.addComponent(simpleBtn.withPosition(Position.create(1, 1)).build())

        screen.addComponent(boxedBtn)
        panel.addComponent(boxedBtn.withPosition(Position.create(1, 3)).build())

        screen.addComponent(tooLongBtn)
        panel.addComponent(tooLongBtn.withPosition(Position.create(1, 7)).build())

        screen.addComponent(overTheTopBtn)
        panel.addComponent(overTheTopBtn.withPosition(Position.create(1, 12)).build())

        screen.addComponent(halfBlockBtn)
        panel.addComponent(halfBlockBtn.withPosition(Position.create(1, 21)).build())

        screen.display()
        screen.theme = theme
    }

}
