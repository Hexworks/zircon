package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.dsl.fragment.buildMenuBar
import org.hexworks.zircon.api.dsl.fragment.dropdownMenu
import org.hexworks.zircon.api.dsl.fragment.menuItem
import org.hexworks.zircon.api.extensions.toScreen

object MenuBarExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                .build()
        ).toScreen()

        screen.display()
        screen.theme = ColorThemes.arc()

        val menuBar = buildMenuBar<String> {
            this.screen = screen
            theme = ColorThemes.arc()
            tileset = screen.tileset
            width = screen.width

            dropdownMenu {
                label = "Left"
                menuItem {
                    label = "File listing"
                    key = "left.list"
                }
            }

            dropdownMenu {
                label = "File"

                menuItem {
                    label = "View"
                    key = "file.view"
                }

                menuItem {
                    label = "Edit"
                    key = "file.edit"
                }
            }

            dropdownMenu {
                label = "Command"

                menuItem {
                    label = "User menu"
                    key = "command.usermenu"
                }

                menuItem {
                    label = "Directory tree"
                    key = "command.tree"
                }
            }

            dropdownMenu {
                label = "Options"

                menuItem {
                    label = "Configuration"
                    key = "options.configuration"
                }
            }

            dropdownMenu {
                label = "Right"

                menuItem {
                    label = "File listing"
                    key = "right.list"
                }

                menuItem {
                    label = "Quick view"
                    key = "right.quickview"
                }
            }
            
            onMenuItemSelected = { item ->
                println("Item selected: $item")
                KeepSubscription
            }
        }

        screen.addFragment(menuBar)
    }
}
