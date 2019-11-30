package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.internal.component.renderer.decoration.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

object PanelsExample {

    private val theme = ColorThemes.techLight()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.panel()
                .withDecorations(box())
                .withSize(18, 5)
                .withComponentRenderer(NoOpComponentRenderer())
                .withAlignment(positionalAlignment(1, 1)))

        screen.addComponent(Components.panel()
                .withDecorations(shadow())
                .withSize(18, 5)
                .withAlignment(positionalAlignment(1, 8)))

        screen.addComponent(Components.panel()
                .withDecorations(box(), shadow())
                .withSize(18, 5)
                .withAlignment(positionalAlignment(1, 15)))

        screen.addComponent(Components.panel()
                .withDecorations(box(boxType = BoxType.DOUBLE))
                .withSize(18, 5)
                .withAlignment(positionalAlignment(1, 22)))

        screen.addComponent(Components.panel()
                .withDecorations(box(boxType = BoxType.BASIC))
                .withSize(18, 5)
                .withAlignment(positionalAlignment(21, 1)))

        screen.addComponent(Components.panel()
                .withDecorations(box(title = "Qux"))
                .withSize(18, 5)
                .withAlignment(positionalAlignment(21, 8)))

        screen.addComponent(Components.panel()
                .withDecorations(
                        HalfBlockDecorationRenderer(),
                        shadow())
                .withSize(18, 5)
                .withAlignment(positionalAlignment(21, 15)))

        screen.addComponent(Components.panel()
                .withSize(18, 5)
                .withDecorations(box(title = "Wombat", boxType = BoxType.TOP_BOTTOM_DOUBLE))
                .withAlignment(positionalAlignment(21, 22)))

        screen.addComponent(Components.panel()
                .withSize(18, 5)
                .withDecorations(box(boxType = BoxType.LEFT_RIGHT_DOUBLE))
                .withAlignment(positionalAlignment(41, 1)))

        val panel = Components.panel()
                .withSize(Sizes.create(18, 19))
                .withDecorations(box(title = "Parent"))
                .withAlignment(positionalAlignment(41, 8))
                .build()

        screen.addComponent(panel)

        val nested0 = Components.panel()
                .withSize(14, 15)
                .withAlignment(positionalAlignment(1, 1))
                .withDecorations(box(title = "Nested 0", boxType = BoxType.DOUBLE))
                .build()

        val nested1 = Components.panel()
                .withSize(10, 11)
                .withAlignment(positionalAlignment(1, 1))
                .withDecorations(box(title = "Nested 1", boxType = BoxType.DOUBLE))
                .build()

        panel.addComponent(nested0)
        nested0.addComponent(nested1)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
