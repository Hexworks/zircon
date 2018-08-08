package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.InputType.*
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType.*
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.component.ContainerHandlerState
import org.codetome.zircon.internal.component.ContainerHandlerState.DEACTIVATED
import org.codetome.zircon.internal.component.ContainerHandlerState.UNKNOWN
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.event.InternalEvent
import org.codetome.zircon.internal.event.InternalEvent.*
import org.codetome.zircon.api.event.EventBus
import org.codetome.zircon.api.event.Subscription
import org.codetome.zircon.internal.config.RuntimeConfig

class DefaultContainerHandler(private var container: DefaultContainer) :
        InternalContainerHandler {

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
            dc.moveTo(dc.getPosition() + container.getEffectivePosition())
            // TODO: if the component has the same size and position it adds it!!!
            require(container.containsBoundable(dc)) {
                "You can't add a component to a container which is not within its bounds " +
                        "(target size: ${container.getEffectiveSize()}, component size: ${dc.getBoundableSize()}" +
                        ", position: ${dc.getPosition()})!"
            }
            require(container.getComponents().none { it.intersects(dc) }) {
                "You can't add a component to a container which intersects with other components!"
            }
            container.addComponent(dc)
            dc.signalAttached()
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
        refreshFocusableLookup()
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
        if(debug) println("Activating container handler")
        state = ContainerHandlerState.ACTIVE
        refreshFocusableLookup()
        subscriptions.add(EventBus.subscribe<InternalEvent.Input> { (input) ->

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
                event = MouseReleased(MouseAction(MOUSE_RELEASED, 1, lastFocusedComponent.getPosition())))
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
                            if (lastFocusedComponent.id == currComponent.id) {
                                EventBus.sendTo(currComponent.id, MouseMoved(mouseAction))
                            }
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
