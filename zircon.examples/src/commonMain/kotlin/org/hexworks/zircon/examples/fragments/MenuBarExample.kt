package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.fragment.dropdownMenu
import org.hexworks.zircon.api.builder.fragment.menuBar
import org.hexworks.zircon.api.builder.fragment.menuItem
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.menu.MenuItemSelected
import org.hexworks.zircon.api.fragment.menu.SelectionCancelled
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * A menu bar in the style of a file manager: each button opens a dropdown in a modal, and
 * the selected item key is shown below.
 */
public val menuBarExample: Example = Example("Menu bar") { context ->
    val size = context.contentSize
    val screen = context.screen
    val root = vBox {
        preferredSize = size
        spacing = 1
    }
    val selected = label {
        +"Pick a menu item"
        preferredSize = Size(size.width, 1)
    }

    val menu = menuBar<String> {
        this.screen = screen
        theme = screen.theme
        tileset = screen.tileset
        width = size.width

        dropdownMenu {
            label = "Left"
            menuItem { label = "File listing"; key = "left.list" }
        }
        dropdownMenu {
            label = "File"
            menuItem { label = "View"; key = "file.view" }
            menuItem { label = "Edit"; key = "file.edit" }
        }
        dropdownMenu {
            label = "Command"
            menuItem { label = "User menu"; key = "command.usermenu" }
            menuItem { label = "Directory tree"; key = "command.tree" }
        }
        dropdownMenu {
            label = "Options"
            menuItem { label = "Configuration"; key = "options.configuration" }
        }
        dropdownMenu {
            label = "Right"
            menuItem { label = "File listing"; key = "right.list" }
            menuItem { label = "Quick view"; key = "right.quickview" }
        }

        onMenuItemSelected = { selection ->
            selected.text = when (selection) {
                is MenuItemSelected -> "Item selected: ${selection.item}"
                SelectionCancelled -> "Selection cancelled"
            }
            KeepSubscription
        }
    }

    root.addComponent(menu.root)
    root.addComponent(selected)
    ExampleView(root)
}
