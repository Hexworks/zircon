package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType

object ButtonsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 40))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(30, 32)
                .withPosition(29, 1)
                .build()
        screen.addComponent(panel)

        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                .withAlignment(positionalAlignment(1, 3))
        val boxedBtn = Components.button()
                .withText("Boxed Button")
                .withDecorations(box())
                .withAlignment(positionalAlignment(1, 5))
        val tooLongBtn = Components.button()
                .withText("Too long name for button")
                .withDecorations(box(), shadow())
                .withAlignment(positionalAlignment(1, 9))
                .withSize(Size.create(10, 4))
        val overTheTopBtn = Components.button()
                .withText("Over the top button")
                .withDecorations(box(boxType = DOUBLE), halfBlock(), shadow())
                .withAlignment(positionalAlignment(1, 14))
        val halfBlockBtn = Components.button()
                .withText("Half block button")
                .withDecorations(
                        halfBlock(), shadow())
                .withAlignment(positionalAlignment(1, 23))
        val invisibleBtn = Components.button()
                .withText("Make me invisible")
                .withDecorations(side())
                .withAlignment(positionalAlignment(1, 30))
                .build()


        screen.addComponent(simpleBtn)
        panel.addComponent(simpleBtn.withAlignment(positionalAlignment(1, 1)).build())

        screen.addComponent(boxedBtn)
        panel.addComponent(boxedBtn.withAlignment(positionalAlignment(1, 3)).build())

        screen.addComponent(tooLongBtn)
        panel.addComponent(tooLongBtn.withAlignment(positionalAlignment(1, 7)).build())

        screen.addComponent(overTheTopBtn)
        panel.addComponent(overTheTopBtn.withAlignment(positionalAlignment(1, 12)).build())

        screen.addComponent(halfBlockBtn)
        panel.addComponent(halfBlockBtn.withAlignment(positionalAlignment(1, 21)).build())

        screen.addComponent(invisibleBtn)

        screen.display()
        screen.theme = theme

        invisibleBtn.processComponentEvents(ComponentEventType.ACTIVATED) {
            invisibleBtn.isHidden = true
        }
    }

}
