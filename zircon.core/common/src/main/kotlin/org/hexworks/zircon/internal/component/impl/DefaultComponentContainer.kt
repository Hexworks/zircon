package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.InputType.*
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.ComponentFocusHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultComponentFocusHandler
import org.hexworks.zircon.internal.component.ContainerHandlerState
import org.hexworks.zircon.internal.component.ContainerHandlerState.DEACTIVATED
import org.hexworks.zircon.internal.component.ContainerHandlerState.UNKNOWN
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAddition
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoval
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.extensions.cancelAll

class DefaultComponentContainer(private var root: RootContainer) :
        InternalComponentContainer,
        ComponentContainer by root,
        ComponentFocusHandler by DefaultComponentFocusHandler(root) {

    private val subscriptions = mutableListOf<Subscription>()
    private val debug = RuntimeConfig.config.debugMode

    private var lastMousePosition = Position.defaultPosition()
    private var state = UNKNOWN
    private var lastHoveredComponent: InternalComponent = root

    private val keyStrokeHandlers = mapOf(
            Pair(NEXT_FOCUS_STROKE, this::focusNext),
            Pair(PREV_FOCUS_STROKE, this::focusPrevious),
            Pair(CLICK_STROKE, this::clickFocused))
            .toMap()

    override fun addComponent(component: Component) {
        (component as? InternalComponent)?.let { dc ->
            root.addComponent(dc)
        } ?: throw IllegalArgumentException(
                "Add a component which does not implement InternalComponent " +
                        "to a ComponentContainer is not allowed.")
        refreshFocusables()
    }

    override fun removeComponent(component: Component): Boolean {
        return root.removeComponent(component).also {
            refreshFocusables()
        }
    }


    override fun isActive(): Boolean = state == ContainerHandlerState.ACTIVE

    override fun activate() {
        if (debug) println("Activating container handler")
        state = ContainerHandlerState.ACTIVE
        refreshFocusables()
        subscriptions.add(Zircon.eventBus.subscribe<ZirconEvent.Input>(ZirconScope) { (input) ->

            keyStrokeHandlers[input]?.invoke()

            val targetComponent = when (input) {
                is KeyStroke -> Maybe.of(focusedComponent)
                is MouseAction -> root.fetchComponentByPosition(input.position)
            }

            targetComponent.map { component ->
                when (input) {
                    is KeyStroke -> {
                        component.inputEmitted(input)
                        component.keyStroked(input)
                    }
                    is MouseAction -> {
                        // this is necessary because listeners are notified this way
                        component.inputEmitted(input)
                        when (input.actionType) {
                            MOUSE_CLICKED -> component.mouseClicked(input)
                            MOUSE_PRESSED -> {
                                focus(component)
                                component.mousePressed(input)
                            }
                            MOUSE_RELEASED -> {
                                focus(component)
                                component.mouseReleased(input)
                            }
                            MOUSE_ENTERED -> {
                                component.mouseEntered(input)
                            }
                            MOUSE_EXITED -> {
                                component.mouseExited(input)
                            }
                            MOUSE_WHEEL_ROTATED_UP -> {
                                component.mouseWheelRotatedUp(input)
                            }
                            MOUSE_WHEEL_ROTATED_DOWN -> {
                                component.mouseWheelRotatedDown(input)
                            }
                            MOUSE_DRAGGED -> {
                                focus(component)
                                component.mouseDragged(input)
                            }
                            MOUSE_MOVED -> handleMouseMoved(input)
                        }
                    }
                }
            }
        })
        subscriptions.add(Zircon.eventBus.subscribe<ComponentAddition>(ZirconScope) {
            refreshFocusables()
        })
        subscriptions.add(Zircon.eventBus.subscribe<ComponentRemoval>(ZirconScope) {
            refreshFocusables()
        })
    }

    override fun deactivate() {
        subscriptions.cancelAll()
        focusedComponent.takeFocus()
        focus(root)
        state = DEACTIVATED
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return root.toFlattenedLayers()
    }

    // TODO: test this!
    private fun clickFocused() {
        Zircon.eventBus.publish(
                event = ZirconEvent.Input(MouseAction(MOUSE_RELEASED, 1, focusedComponent.absolutePosition)),
                eventScope = ZirconScope)
    }

    private fun handleMouseMoved(mouseAction: MouseAction) {
        if (mouseAction.position != lastMousePosition) {
            lastMousePosition = mouseAction.position
            root.fetchComponentByPosition(lastMousePosition).map { currentComponent ->

                val lastHoveredComponentId = lastHoveredComponent.id
                if (lastHoveredComponentId == currentComponent.id) {
                    currentComponent.mouseMoved(mouseAction)
                } else {
                    // we also need to emit input because listeners
                    // dispatch on the input event
                    lastHoveredComponent.inputEmitted(mouseAction.copy(actionType = MOUSE_EXITED))
                    lastHoveredComponent.mouseExited(mouseAction)
                    lastHoveredComponent = currentComponent
                    currentComponent.inputEmitted(mouseAction.copy(actionType = MOUSE_ENTERED))
                    currentComponent.mouseEntered(mouseAction)
                }
            }
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return root.applyColorTheme(colorTheme)
    }

    companion object {
        val NEXT_FOCUS_STROKE = KeyStroke(type = Tab)
        val PREV_FOCUS_STROKE = KeyStroke(shiftDown = true, type = ReverseTab)
        val CLICK_STROKE = KeyStroke(type = Character, character = ' ')
    }
}
