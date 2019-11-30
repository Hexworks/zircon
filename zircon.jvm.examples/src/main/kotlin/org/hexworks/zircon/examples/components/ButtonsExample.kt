package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.halfBlock
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.extensions.side
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.uievent.MouseEventType

object ButtonsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 40))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

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
                .withSize(Sizes.create(10, 4))
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
        screen.applyColorTheme(theme)

        invisibleBtn.processMouseEvents(MouseEventType.MOUSE_CLICKED) { _, _ ->
            invisibleBtn.isHidden = true
        }
    }

}
