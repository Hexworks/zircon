package org.hexworks.zircon.internal.integration


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_LEFT
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.LEFT_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.RIGHT_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_LEFT
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.screen.Screen

object ComponentAlignmentTest {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val panel = Components.panel()
                .withDecorations(box(title = "Component alignment test"), shadow())
                .withSize(Size.create(30, 15))
                .withAlignmentWithin(screen, CENTER)
                .build()
        screen.addComponent(panel)

        panel.addComponent(Components.label()
                .withText("TL")
                .withAlignmentWithin(panel, TOP_LEFT)
                .build())

        panel.addComponent(Components.label()
                .withText("TC")
                .withAlignmentWithin(panel, TOP_CENTER)
                .build())

        panel.addComponent(Components.label()
                .withText("TR")
                .withAlignmentWithin(panel, TOP_RIGHT)
                .build())

        panel.addComponent(Components.label()
                .withText("RC")
                .withAlignmentWithin(panel, RIGHT_CENTER)
                .build())

        panel.addComponent(Components.label()
                .withText("BR")
                .withAlignmentWithin(panel, BOTTOM_RIGHT)
                .build())

        panel.addComponent(Components.label()
                .withText("BC")
                .withAlignmentWithin(panel, BOTTOM_CENTER)
                .build())

        panel.addComponent(Components.label()
                .withText("BL")
                .withAlignmentWithin(panel, BOTTOM_LEFT)
                .build())

        panel.addComponent(Components.label()
                .withText("LC")
                .withAlignmentWithin(panel, LEFT_CENTER)
                .build())

        panel.addComponent(Components.label()
                .withText("CENTER")
                .withAlignmentWithin(panel, CENTER)
                .build())

        // positioning labels around the panel
        screen.addComponent(Components.label()
                .withText("TL")
                .withAlignmentAround(panel, TOP_LEFT)
                .build())

        screen.addComponent(Components.label()
                .withText("TC")
                .withAlignmentAround(panel, TOP_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("TR")
                .withAlignmentAround(panel, TOP_RIGHT)
                .build())

        screen.addComponent(Components.label()
                .withText("RC")
                .withAlignmentAround(panel, RIGHT_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("BR")
                .withAlignmentAround(panel, BOTTOM_RIGHT)
                .build())

        screen.addComponent(Components.label()
                .withText("BC")
                .withAlignmentAround(panel, BOTTOM_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("BL")
                .withAlignmentAround(panel, BOTTOM_LEFT)
                .build())

        screen.addComponent(Components.label()
                .withText("LC")
                .withAlignmentAround(panel, LEFT_CENTER)
                .build())

        // positioning components within the screen
        screen.addComponent(Components.label()
                .withText("TL")
                .withAlignmentWithin(screen, TOP_LEFT)
                .build())

        screen.addComponent(Components.label()
                .withText("TC")
                .withAlignmentWithin(screen, TOP_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("TR")
                .withAlignmentWithin(screen, TOP_RIGHT)
                .build())

        screen.addComponent(Components.label()
                .withText("RC")
                .withAlignmentWithin(screen, RIGHT_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("BR")
                .withAlignmentWithin(screen, BOTTOM_RIGHT)
                .build())

        screen.addComponent(Components.label()
                .withText("BC")
                .withAlignmentWithin(screen, BOTTOM_CENTER)
                .build())

        screen.addComponent(Components.label()
                .withText("BL")
                .withAlignmentWithin(screen, BOTTOM_LEFT)
                .build())

        screen.addComponent(Components.label()
                .withText("LC")
                .withAlignmentWithin(screen, LEFT_CENTER)
                .build())

        screen.display()
        screen.theme = theme
    }

}
