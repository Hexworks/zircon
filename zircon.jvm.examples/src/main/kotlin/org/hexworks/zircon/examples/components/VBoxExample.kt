package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.application.AppConfig

import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_LEFT
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED

object VBoxExample {

    private val THEME = ColorThemes.amigaOs()
    private val TILESET = CP437TilesetResources.rexPaint20x20()

    var count = 0

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val vbox = Components.vbox()
                .withSpacing(0)
                .withSize(20, 25)
                .withDecorations(box(title = "VBox"))
                .withAlignmentWithin(screen, CENTER)
                .build()

        0.until(5).forEach {
            addButton(vbox)
        }

        val addNew = Components.button()
                .withText("Add new")
                .withAlignmentWithin(screen, TOP_LEFT)
                .build().apply {
                    processComponentEvents(ACTIVATED) {
                        addButton(vbox)
                    }
                }

        screen.addComponent(vbox)
        screen.addComponent(addNew)

        screen.display()
        screen.theme = THEME
    }

    private fun addButton(vbox: VBox) {
        vbox.addComponent(Components.button()
                .withText("Remove: $count")
                .build().apply {
                    theme = THEME
                    processComponentEvents(ACTIVATED) {
                        vbox.removeComponent(this)
                    }
                })
        count++
    }

}
