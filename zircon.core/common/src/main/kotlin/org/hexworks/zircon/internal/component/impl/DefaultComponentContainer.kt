package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
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

class DefaultComponentContainer(private var container: RootContainer) :
        InternalComponentContainer,
        ComponentContainer by container,
        ComponentFocusHandler by DefaultComponentFocusHandler(container) {

    private var lastMousePosition = Position.defaultPosition()
    private var state = UNKNOWN
    private val subscriptions = mutableListOf<Subscription>()
    private val debug = RuntimeConfig.config.debugMode
    private var lastHoveredComponent: InternalComponent = container

    private val keyStrokeHandlers = mapOf(
            Pair(NEXT_FOCUS_STROKE, this::focusNext),
            Pair(PREV_FOCUS_STROKE, this::focusPrevious),
            Pair(CLICK_STROKE, this::clickFocused))
            .toMap()

    override fun addComponent(component: Component) {
        (component as? InternalComponent)?.let { dc ->
            container.addComponent(dc)
        } ?: throw IllegalArgumentException(
                "Add a component which does not implement InternalComponent " +
                        "to a ComponentContainer is not allowed.")
        refreshFocusables()
    }

    override fun removeComponent(component: Component): Boolean {
        return container.removeComponent(component).also {
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

            when (input) {
                is KeyStroke -> {
                    focusedComponent.inputEmitted(input)
                    focusedComponent.keyStroked(input)
                }
                is MouseAction -> {
                    val component = container.fetchComponentByPosition(input.position)
                    component.map {
                        // this is necessary because listeners are notified this way
                        it.inputEmitted(input)
                    }
                    when (input.actionType) {
                        MOUSE_CLICKED -> component.map { it.mouseClicked(input) }
                        MOUSE_PRESSED -> component.map {
                            focus(it)
                            it.mousePressed(input)
                        }
                        MOUSE_RELEASED -> component.map {
                            focus(it)
                            it.mouseReleased(input)
                        }
                        MOUSE_ENTERED -> component.map { it.mouseEntered(input) }
                        MOUSE_EXITED -> component.map { it.mouseExited(input) }
                        MOUSE_WHEEL_ROTATED_UP -> component.map { it.mouseWheelRotatedUp(input) }
                        MOUSE_WHEEL_ROTATED_DOWN -> component.map { it.mouseWheelRotatedDown(input) }
                        MOUSE_DRAGGED -> component.map {
                            focus(it)
                            it.mouseDragged(input)
                        }
                        MOUSE_MOVED -> handleMouseMoved(input)
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
        subscriptions.forEach {
            it.cancel()
        }
        subscriptions.clear()
        focusedComponent.takeFocus()
        focus(container)
        state = DEACTIVATED
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return container.toFlattenedLayers()
    }

    // TODO: test this!
    private fun clickFocused() {
        Zircon.eventBus.broadcast(
                event = ZirconEvent.Input(MouseAction(MOUSE_RELEASED, 1, focusedComponent.absolutePosition)),
                eventScope = ZirconScope)
    }

    private fun handleMouseMoved(mouseAction: MouseAction) {
        if (mouseAction.position != lastMousePosition) {
            lastMousePosition = mouseAction.position
            container.fetchComponentByPosition(lastMousePosition).map { currentComponent ->

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

    companion object {
        val NEXT_FOCUS_STROKE = KeyStroke(type = Tab)
        val PREV_FOCUS_STROKE = KeyStroke(shiftDown = true, type = ReverseTab)
        val CLICK_STROKE = KeyStroke(type = Character, character = ' ')
    }
}
