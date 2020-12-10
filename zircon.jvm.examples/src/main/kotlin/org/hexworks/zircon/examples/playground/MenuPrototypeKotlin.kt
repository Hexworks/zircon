package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.toScreen

object MenuPrototypeKotlin {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                .build()
        ).toScreen()

        screen.display()
        screen.theme = ColorThemes.arc()

        val menuBar = MenuBar(
            menuElements = listOf(
                MenuBarItem(
                    "Left", listOf(
                        MenuItem("files.list", "File Listing"),
                        MenuItem("view.quick", "Quick view")
                    )
                ),
                MenuBarItem(
                    "File", listOf(
                        MenuItem("file.view", "View"),
                        MenuItem("file.edit", "Edit")
                    )
                ),
                MenuBarItem(
                    "Command", listOf(
                        MenuItem("menu.user", "User menu"),
                        MenuItem("directory.tree", "Directory tree")
                    )
                ),
                MenuBarItem(
                    "Options", listOf(
                        MenuItem("configuration", "Configuration")
                    )
                ),
                MenuBarItem(
                    "Right", listOf(
                        MenuItem("files.list", "File Listing"),
                        MenuItem("view.quick", "Quick view")
                    )
                )
            ),
            spacing = 1,
            width = screen.width,
            screen = screen,
            theme = ColorThemes.arc()
        )

        screen.addFragment(menuBar)
    }
}

