package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.examples.base.DEFAULT_THEME
import org.hexworks.zircon.examples.base.DEFAULT_TILESET
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import java.awt.Dimension
import java.awt.Toolkit


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val resolution = Dimension(1600, 900)
        val gridSize = Dimension(80, 45)
        val tileHeight = resolution.height / gridSize.height

        val screen = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withDefaultTileset(TrueTypeFontResources.ibmBios(tileHeight))
                        .withSize(gridSize.width, gridSize.height)
                        .build()).toScreen()

        screen.addComponent(
                Components.panel()
                        .withSize(30, 20)
                        .withDecorations(box(title = "hey"))
                        .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                        .build())

        screen.display()
        screen.theme = ColorThemes.amigaOs()
    }

}

