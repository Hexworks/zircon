package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.Pass

class MenuBar(
    private val screen: Screen,
    private val menuElements: List<MenuBarItem>,
    theme: ColorTheme,
    tileset: TilesetResource,
    private val spacing: Int = 1,
    width: Int = menuElements.minSize(spacing).width,
) : Fragment {

    override val root = Components.hbox()
        .withSpacing(spacing)
        .withPreferredSize(width, 1)
        .build()

    private val size = Size.create(width, 1)

    init {
        require(size.width >= menuElements.minSize(spacing).width) {
            "Contents wouldn't fit within required width: $width"
        }
        menuElements.forEach { menuBarItem ->
            require(menuBarItem.children.isNotEmpty()) {
                "A menu bar item has to have at least 1 child."
            }
            val menuButton = Components.button()
                .withText(menuBarItem.label)
                .withDecorations(margin(0, 0, 0, 1))
                .build()
            menuButton.onActivated {
                menuButton.resetState()
                val children = menuBarItem.children
                val modalWidth = (children.map { it.label.length }.maxOrNull() ?: 0) + 2
                val modalSize = Size.create(modalWidth, children.size + 2)
                val menuItems = Components.vbox()
                    .withDecorations(box())
                    .withPosition(Position.bottomLeftOf(menuButton))
                    .withSize(modalSize)
                    .withColorTheme(theme)
                    .build()

                val modal = ModalBuilder.newBuilder<MenuSelection>()
                    .withCenteredDialog(false)
                    .withComponent(menuItems)
                    .withPreferredSize(screen.size)
                    .withColorTheme(theme)
                    .withTileset(tileset)
                    .withDarkenPercent(0.0)
                    .build()

                modal.handleMouseEvents(MOUSE_PRESSED) { event, phase ->
                    if (menuItems.containsPosition(event.position).not()) {
                        modal.close(MenuSelection(key = null, cancel = true))
                    }
                    Pass
                }

                modal.onClosed {
                    println("Closed with $it")
                }

                children.filterIsInstance(MenuItem::class.java).forEach { menuItem ->
                    val menuItemButton = Components.button()
                        .withDecorations()
                        .withText(menuItem.label)
                        .withColorTheme(theme)
                        .build()
                    menuItems.addComponent(menuItemButton)
                    menuItemButton.onActivated {
                        modal.close(MenuSelection(menuItem.key))
                    }
                }
                screen.openModal(modal)
            }
            root.addComponent(menuButton)
        }
    }

    data class MenuSelection(
        val key: String?,
        val cancel: Boolean = false
    ) : ModalResult

    companion object {
        private fun List<MenuBarItem>.minSize(spacing: Int) = Size.create(
            width = map { it.label.length }.fold(0, Int::plus) + (size - 1) * spacing + 2,
            height = 1
        )
    }
}
