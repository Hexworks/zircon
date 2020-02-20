package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

object PanelsTest {

    private val theme = ColorThemes.techLight()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        screen.addComponent(Components.panel()
                .withDecorations(box())
                .withSize(Size.create(18, 5))
                .withComponentRenderer(NoOpComponentRenderer())
                .withPosition(Position.create(1, 1)))

        screen.addComponent(Components.panel()
                .withDecorations(shadow())
                .withSize(Size.create(18, 5))
                .withPosition(Position.create(1, 8)))

        screen.addComponent(Components.panel()
                .withDecorations(box(), shadow())
                .withSize(Size.create(18, 5))
                .withPosition(Position.create(1, 15)))

        screen.addComponent(Components.panel()
                .withDecorations(box(BoxType.DOUBLE))
                .withSize(Size.create(18, 5))
                .withPosition(Position.create(1, 22)))

        screen.addComponent(Components.panel()
                .withDecorations(box(BoxType.BASIC))
                .withSize(18, 5)
                .withPosition(21, 1))

        screen.addComponent(Components.panel()
                .withDecorations(box(title = "Qux"))
                .withSize(18, 5)
                .withPosition(21, 8))

        screen.addComponent(Components.panel()
                .withDecorations(
                        shadow(),
                        halfBlock())
                .withSize(18, 5)
                .withPosition(21, 15))

        screen.addComponent(Components.panel()
                .withSize(18, 5)
                .withDecorations(box(title = "Wombat"))
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE))
                .withPosition(21, 22))

        screen.addComponent(Components.panel()
                .withSize(18, 5)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE))
                .withPosition(41, 1))

        val panel = Components.panel()
                .withSize(18, 19)
                .withDecorations(box(title = "Parent"))
                .withPosition(41, 8)
                .build()

        screen.addComponent(panel)

        val nested0 = Components.panel()
                .withSize(14, 15)
                .withPosition(1, 1)
                .withDecorations(box(
                        boxType = BoxType.DOUBLE,
                        title = "Nested 0"))
                .build()

        val nested1 = Components.panel()
                .withSize(Size.create(10, 11))
                .withPosition(Position.create(1, 1))
                .withDecorations(box(
                        boxType = BoxType.DOUBLE,
                        title = "Nested 1"))
                .build()

        panel.addComponent(nested0)
        nested0.addComponent(nested1)

        screen.display()
        screen.theme = theme
    }

}
