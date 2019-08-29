package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType

object ButtonsTest {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(Sizes.create(30, 28))
                .withPosition(Positions.create(29, 1))
                .build()
        screen.addComponent(panel)

        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                .withPosition(Positions.create(1, 3))
        val boxedBtn = Components.button()
                .withText("Boxed Button")
                .withDecorations(box())
                .withPosition(Positions.create(1, 5))
        val tooLongBtn = Components.button()
                .withText("Too long name for button")
                .withDecorations(box(), shadow())
                .withPosition(Positions.create(1, 9))
                .withSize(Sizes.create(10, 4))
        val overTheTopBtn = Components.button()
                .withText("Over the top button")
                .withDecorations(
                        HalfBlockDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        ShadowDecorationRenderer())
                .withPosition(Positions.create(1, 14))
        val halfBlockBtn = Components.button()
                .withText("Half block button")
                .withDecorations(
                        HalfBlockDecorationRenderer(),
                        ShadowDecorationRenderer())
                .withPosition(Positions.create(1, 23))


        screen.addComponent(simpleBtn)
        panel.addComponent(simpleBtn.withPosition(Positions.create(1, 1)).build())

        screen.addComponent(boxedBtn)
        panel.addComponent(boxedBtn.withPosition(Positions.create(1, 3)).build())

        screen.addComponent(tooLongBtn)
        panel.addComponent(tooLongBtn.withPosition(Positions.create(1, 7)).build())

        screen.addComponent(overTheTopBtn)
        panel.addComponent(overTheTopBtn.withPosition(Positions.create(1, 12)).build())

        screen.addComponent(halfBlockBtn)
        panel.addComponent(halfBlockBtn.withPosition(Positions.create(1, 21)).build())

        screen.display()
        screen.applyColorTheme(theme)
    }

}
