@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.BoxType


object KotlinPlayground {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val theme = ColorThemes.ghostOfAChance()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = tileGrid.toScreen()

        val prop = "".toProperty()

        val panel = Components.panel()
                .withSize(18, 5)
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Test"))
                .withAlignmentWithin(screen, CENTER)
                .build()

        panel.titleProperty.bind(prop)

        prop.value = "does not get reflected"
        screen.addComponent(panel)



        screen.display()
        screen.theme = theme
    }
}
