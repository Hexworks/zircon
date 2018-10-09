package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.event.EventBusSubscription
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.InputType.*
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.component.ContainerHandlerState
import org.hexworks.zircon.internal.component.ContainerHandlerState.DEACTIVATED
import org.hexworks.zircon.internal.component.ContainerHandlerState.UNKNOWN
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAddition
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoval

class DefaultComponentContainer(private var container: RootContainer) :
        InternalComponentContainer,
        ComponentContainer by container {

    private var lastMousePosition = Position.defaultPosition()
    private var lastHoveredComponent: InternalComponent = container
    private var lastFocusedComponent: InternalComponent = container
    private var state = UNKNOWN
    private val subscriptions = mutableListOf<EventBusSubscription<*>>()
    private val nextsLookup = mutableMapOf<Identifier, InternalComponent>(Pair(container.id, container))
    private val prevsLookup = nextsLookup.toMutableMap()
    private val debug = RuntimeConfig.config.debugMode

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
        refreshFocusableLookup()
    }

    override fun removeComponent(component: Component): Boolean {
        return container.removeComponent(component).also {
            refreshFocusableLookup()
        }
    }


    override fun isActive(): Boolean = state == ContainerHandlerState.ACTIVE

    override fun activate() {
        if (debug) println("Activating container handler")
        state = ContainerHandlerState.ACTIVE
        refreshFocusableLookup()
        subscriptions.add(EventBus.subscribe<ZirconEvent.Input> { (input) ->

            keyStrokeHandlers[input]?.invoke()

            when (input) {
                is KeyStroke -> {
                    lastFocusedComponent.inputEmitted(input)
                    lastFocusedComponent.keyStroked(input)
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
                            focusComponent(it)
                            it.mousePressed(input)
                        }
                        MOUSE_RELEASED -> component.map {
                            focusComponent(it)
                            it.mouseReleased(input)
                        }
                        MOUSE_ENTERED -> component.map { it.mouseEntered(input) }
                        MOUSE_EXITED -> component.map { it.mouseExited(input) }
                        MOUSE_WHEEL_ROTATED_UP -> component.map { it.mouseWheelRotatedUp(input) }
                        MOUSE_WHEEL_ROTATED_DOWN -> component.map { it.mouseWheelRotatedDown(input) }
                        MOUSE_DRAGGED -> component.map {
                            focusComponent(it)
                            it.mouseDragged(input)
                        }
                        MOUSE_MOVED -> handleMouseMoved(input)
                    }
                }
            }
        })
        subscriptions.add(EventBus.subscribe<ComponentAddition> {
            refreshFocusableLookup()
        })
        subscriptions.add(EventBus.subscribe<ComponentRemoval> {
            refreshFocusableLookup()
        })
    }

    override fun deactivate() {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        subscriptions.clear()
        lastFocusedComponent.takeFocus()
        lastFocusedComponent = container
        state = DEACTIVATED
    }

    override fun transformComponentsToLayers(): List<Layer> {
        return container.transformToLayers()
    }

    // TODO: test this!
    private fun clickFocused() {
        EventBus.broadcast(ZirconEvent.Input(MouseAction(MOUSE_RELEASED, 1, lastFocusedComponent.absolutePosition)))
    }

    // TODO: factor these out to FocusHandler
    private fun focusComponent(component: InternalComponent) {
        if (component.acceptsFocus() && isNotAlreadyFocused(component)) {
            lastFocusedComponent.takeFocus()
            lastFocusedComponent = component
            component.giveFocus()
        }
    }

    private fun focusNext() = nextsLookup[lastFocusedComponent.id]?.let { focusComponent(it) }

    private fun focusPrevious() = prevsLookup[lastFocusedComponent.id]?.let { focusComponent(it) }

    private fun isNotAlreadyFocused(component: InternalComponent) =
            lastFocusedComponent.id != component.id

    private fun refreshFocusableLookup() {
        nextsLookup.clear()
        prevsLookup.clear()

        val tree = container.fetchFlattenedComponentTree().filter { it.acceptsFocus() }
        if (tree.isNotEmpty()) {
            val first = tree.first()
            nextsLookup[container.id] = first
            prevsLookup[first.id] = container
            var prev = first

            tree.iterator().let { treeIter ->
                treeIter.next() // first already handled
                while (treeIter.hasNext()) {
                    val next = treeIter.next()
                    nextsLookup[prev.id] = next
                    prevsLookup[next.id] = prev
                    prev = next
                }
            }
            nextsLookup[prev.id] = container
            prevsLookup[container.id] = prev
            lastFocusedComponent = container
        }
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
