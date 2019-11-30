package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_LEFT
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED

object HBoxExample {

    private val THEME = ColorThemes.techLight()
    private val TILESET = CP437TilesetResources.rexPaint20x20()

    var count = 0

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withDebugMode(true)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val hbox = Components.hbox()
                .withSpacing(0)
                .withSize(50, 15)
                .withDecorations(box(title = "HBox"))
                .withAlignmentWithin(screen, CENTER)
                .build()

        0.until(3).forEach {
            addButton(hbox)
        }

        val addNew = Components.button()
                .withText("Add new")
                .withAlignmentWithin(screen, TOP_LEFT)
                .build().apply {
                    processComponentEvents(ACTIVATED) {
                        addButton(hbox)
                    }
                }

        screen.addComponent(hbox)
        screen.addComponent(addNew)

        screen.display()
        screen.theme = THEME
    }

    private fun addButton(hbox: HBox) {
        hbox.addComponent(Components.button()
                .withText("Remove: $count")
                .withSize(12, 1)
                .build().apply {
                    theme = THEME
                    processComponentEvents(ACTIVATED) {
                        hbox.removeComponent(this)
                    }
                })
        count++
    }

}
