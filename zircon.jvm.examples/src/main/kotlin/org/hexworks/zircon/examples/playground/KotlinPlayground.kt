package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.examples.base.DEFAULT_THEME
import org.hexworks.zircon.examples.base.DEFAULT_TILESET
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val size = Size.create(60, 40)

        val screen = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
                .withSize(size.plus(Size.create(2, 2)))
                .build()).toScreen()

        val heading = Components.hbox()
                .withSize(size.width, 5)
                .build()

        val controls = Components.panel()
                .withSize(30, 1)
                .build()

        controls.addComponent(Components.label().withText("Pick a tileset"))

        heading.addComponent(Components.header().withText("title").withSize(30, 1).build())
        heading.addComponent(controls)

        screen.addComponent(heading)
        screen.display()
        screen.theme = ColorThemes.amigaOs()
    }

}

