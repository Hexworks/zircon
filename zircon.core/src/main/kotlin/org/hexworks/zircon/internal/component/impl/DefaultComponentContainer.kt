package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.event.Subscription
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.InputType.*
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.component.ContainerHandlerState
import org.hexworks.zircon.internal.component.ContainerHandlerState.DEACTIVATED
import org.hexworks.zircon.internal.component.ContainerHandlerState.UNKNOWN
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.*

class DefaultComponentContainer(private var container: DefaultContainer) :
        InternalComponentContainer {

    private var lastMousePosition = Position.defaultPosition()
    private var lastHoveredComponentId = Identifier.randomIdentifier()
    private var lastFocusedComponent: InternalComponent = container
    private var state = UNKNOWN
    private val subscriptions = mutableListOf<Subscription<*>>()
    private val nextsLookup = mutableMapOf<Identifier, InternalComponent>(Pair(container.id, container))
    private val prevsLookup = nextsLookup.toMutableMap()
    private val debug = RuntimeConfig.config.debugMode

    private val keyStrokeHandlers = mapOf(
            Pair(NEXT_FOCUS_STROKE, this::focusNext),
            Pair(PREV_FOCUS_STROKE, this::focusPrevious),
            Pair(CLICK_STROKE, this::clickFocused))
            .toMap()

    override fun addComponent(component: Component) {
        (component as? DefaultComponent)?.let { dc ->
            require(container.containsBoundable(dc)) {
                "You can't add a component to a container which is not within its bounds " +
                        "(target size: ${container.getEffectiveSize()}, component size: ${dc.size()}" +
                        ", position: ${dc.position()})!"
            }
            require(container.getComponents().none { it.intersects(dc) }) {
                "You can't add a component to a container which intersects with other components!"
            }
            require(container.getComponents().none { it.containsBoundable(dc) }) {
                "You can't add a component to a container which intersects with other components!"
            }
            container.addComponent(dc)
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
        refreshFocusableLookup()
    }

    override fun addComponent(builder: Builder<Component>) {
        addComponent(builder.build())
    }

    override fun removeComponent(component: Component) =
            container.removeComponent(component).also {
                refreshFocusableLookup()
            }

    override fun applyColorTheme(colorTheme: ColorTheme) {
        container.applyColorTheme(colorTheme)
    }

    override fun isActive() = state == ContainerHandlerState.ACTIVE

    override fun activate() {
        if (debug) println("Activating container handler")
        state = ContainerHandlerState.ACTIVE
        refreshFocusableLookup()
        subscriptions.add(EventBus.subscribe<ZirconEvent.Input> { (input) ->

            keyStrokeHandlers[input]?.invoke()

            if (input is KeyStroke) {
                EventBus.broadcast(KeyPressed(input))
            }

            if (input is MouseAction) {
                when (input.actionType) {
                    MOUSE_MOVED -> handleMouseMoved(input)

                    MOUSE_PRESSED -> container
                            .fetchComponentByPosition(input.position)
                            .map { component ->
                                focusComponent(component)
                                EventBus.sendTo(component.id, MousePressed(input))
                            }

                    MOUSE_RELEASED -> container
                            .fetchComponentByPosition(input.position)
                            .map { component ->
                                focusComponent(component)
                                EventBus.sendTo(component.id, MouseReleased(input))
                            }

                    MOUSE_DRAGGED -> container
                            .fetchComponentByPosition(input.position)
                            .map { component ->
                                focusComponent(component)
                                EventBus.sendTo(component.id, MouseDragged(input))
                            }

                    else -> {
                        // we don't handle other actions yet
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

    private fun clickFocused() {
        EventBus.sendTo(
                identifier = lastFocusedComponent.id,
                event = MouseReleased(MouseAction(MOUSE_RELEASED, 1, lastFocusedComponent.position())))
    }

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
            container.fetchComponentByPosition(lastMousePosition)
                    .map { currComponent ->
                        if (lastHoveredComponentId == currComponent.id) {
                            EventBus.sendTo(currComponent.id, MouseMoved(mouseAction))
                        } else {
                            EventBus.sendTo(lastHoveredComponentId, MouseOut(mouseAction))
                            lastHoveredComponentId = currComponent.id
                            EventBus.sendTo(currComponent.id, MouseOver(mouseAction))
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
