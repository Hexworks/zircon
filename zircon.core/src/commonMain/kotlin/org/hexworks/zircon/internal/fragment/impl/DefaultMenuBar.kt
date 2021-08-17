package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.events.api.*
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.noDecoration
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildButton
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.buildModal
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.fragment.MenuBar
import org.hexworks.zircon.api.fragment.menu.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.Pass

@Beta
class DefaultMenuBar<T : Any> internal constructor(
    private val screen: Screen,
    position: Position,
    width: Int,
    spacing: Int,
    theme: ColorTheme,
    tileset: TilesetResource,
    menuElements: List<DropdownMenu<T>>,
) : MenuBar<T> {

    override val root = buildHbox {
        this.position = position
        this.preferredContentSize = Size.create(width, 1)
        this.colorTheme = theme
        this.tileset = tileset
        this.spacing = spacing
    }

    private val eventBus = screen.asInternal().application.asInternal().eventBus
    private val eventScope = screen.asInternal().application.asInternal().eventScope

    init {
        menuElements.forEach { dropdownMenu ->
            val children = dropdownMenu.children

            val menuButton = buildButton {
                +dropdownMenu.label
                decoration = noDecoration()
            }
            menuButton.onActivated {

                val menuItems = buildVbox {
                    preferredContentSize = Size.create(
                        width = children.minMenuWidth,
                        height = children.size
                    )
                    this.position = Position.bottomLeftOf(menuButton)
                    decoration = box()
                }

                val modal = buildModal<MenuSelection<T>> {
                    centeredDialog = false
                    contentComponent = menuItems
                    preferredSize = screen.size
                    colorTheme = theme
                    this.tileset = tileset
                    darkenPercent = 0.0
                }
                modal.handleMouseEvents(MOUSE_PRESSED) { event, _ ->
                    if (menuItems.containsPosition(event.position).not()) {
                        modal.close(SelectionCancelled)
                    }
                    // We need to pass on this so that the container below this one
                    // has a chance at handling this event. This is necessary when
                    // the user opens a menu for example, and decides to click on
                    // another menu item. In that case the current modal is closed
                    // and another one is opened
                    Pass
                }

                modal.onClosed {
                    eventBus.publish(
                        event = ItemSelectionEvent(
                            emitter = this,
                            menuSelection = it
                        ),
                        eventScope = eventScope
                    )
                }

                children.forEach { menuItem ->
                    menuItems.addComponent(buildButton {
                        +menuItem.label
                        colorTheme = theme
                        decoration = noDecoration()
                        onActivated {
                            modal.close(MenuItemSelected(menuItem.key))
                        }
                    })
                }
                screen.openModal(modal)
            }
            root.addComponent(menuButton)
        }
    }

    override fun onMenuItemSelected(
        handler: (menuSelection: MenuSelection<T>) -> CallbackResult
    ): Subscription = eventBus.simpleSubscribeTo<ItemSelectionEvent<T>>(eventScope) {
        handler(it.menuSelection)
    }

    private data class ItemSelectionEvent<T : Any>(
        override val emitter: Any,
        val menuSelection: MenuSelection<T>,
    ) : Event

    private val List<DropdownMenuItem<T>>.minMenuWidth: Int
        get() = map { it.width }.maxOrNull() ?: 0
}
