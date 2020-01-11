@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer


object KotlinPlayground {

    private val theme = ColorThemes.techLight()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)


        val component = Components.button()
                .withDecorations(box())
                .withSize(18, 5)
                .withComponentRenderer(NoOpComponentRenderer())
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .build()

        screen.addComponent(component)

        screen.display()
        screen.theme = theme
    }
}
