package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType.*
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType.*
import org.codetome.zircon.internal.component.ContainerHandlerState.*
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType.*
import org.codetome.zircon.internal.event.Subscription
import java.util.*

class DefaultContainerHandler(private var container: DefaultContainer) : InternalContainerHandler {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()
    private var lastFocusedComponent: InternalComponent = container
    private var state = UNKNOWN
    private val subscriptions = mutableListOf<Subscription<*>>()
    private val nextsLookup = mutableMapOf<UUID, InternalComponent>(Pair(container.getId(), container))
    private val prevsLookup = nextsLookup.toMutableMap()

    private val keyStrokeHandlers = mapOf(
            Pair(NEXT_FOCUS_STROKE, this::focusNext),
            Pair(PREV_FOCUS_STROKE, this::focusPrevious),
            Pair(CLICK_STROKE, this::clickFocused))
            .toMap()

    @Synchronized
    override fun addComponent(component: Component) {
        container.addComponent(component)
        refreshFocusableLookup()
        EventBus.emit(ComponentChange)
    }

    @Synchronized
    override fun removeComponent(component: Component) =
            container.removeComponent(component).also {
                refreshFocusableLookup()
                EventBus.emit(ComponentChange)
            }

    @Synchronized
    override fun applyColorTheme(colorTheme: ColorTheme) {
        container.applyColorTheme(colorTheme)
        EventBus.emit(ComponentChange)
    }

    override fun isActive() = state == ACTIVE

    @Synchronized
    override fun activate() {
        state = ACTIVE
        refreshFocusableLookup()
        subscriptions.add(EventBus.subscribe<Input>(Input, { (input) ->

            keyStrokeHandlers[input]?.invoke()

            if (input is KeyStroke) {
                EventBus.emit(KeyPressed, input)
            }

            if (input is MouseAction) {
                when (input.actionType) {
                    MOUSE_MOVED -> handleMouseMoved(input)

                    MOUSE_PRESSED -> container
                            .fetchComponentByPosition(input.position)
                            .map { component ->
                                focusComponent(component)
                                EventBus.emit(MousePressed(component.getId()), input)
                            }

                    MOUSE_RELEASED -> container
                            .fetchComponentByPosition(input.position)
                            .map { component ->
                                focusComponent(component)
                                EventBus.emit(MouseReleased(component.getId()), input)
                            }
                    else -> {
                        // we don't handle other actions yet
                    }
                }
            }
        }))
        subscriptions.add(EventBus.subscribe(ComponentAddition, {
            refreshFocusableLookup()
        }))
        subscriptions.add(EventBus.subscribe(ComponentRemoval, {
            refreshFocusableLookup()
        }))
    }

    @Synchronized
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
        EventBus.emit(
                type = MouseReleased(lastFocusedComponent.getId()),
                data = MouseAction(MOUSE_RELEASED, 1, lastFocusedComponent.getPosition()))
    }

    private fun focusComponent(component: InternalComponent) {
        if (component.acceptsFocus() && isNotAlreadyFocused(component)) {
            lastFocusedComponent.takeFocus()
            lastFocusedComponent = component
            component.giveFocus()
        }
    }

    private fun focusNext() = nextsLookup[lastFocusedComponent.getId()]?.let { focusComponent(it) }

    private fun focusPrevious() = prevsLookup[lastFocusedComponent.getId()]?.let { focusComponent(it) }

    private fun isNotAlreadyFocused(component: InternalComponent) =
            lastFocusedComponent.getId() != component.getId()

    private fun refreshFocusableLookup() {
        nextsLookup.clear()
        prevsLookup.clear()

        val tree = container.fetchFlattenedComponentTree().filter { it.acceptsFocus() }
        if (tree.isNotEmpty()) {
            val first = tree.first()
            nextsLookup[container.getId()] = first
            prevsLookup[first.getId()] = container
            var prev = first

            tree.iterator().let { treeIter ->
                treeIter.next() // first already handled
                while (treeIter.hasNext()) {
                    val next = treeIter.next()
                    nextsLookup[prev.getId()] = next
                    prevsLookup[next.getId()] = prev
                    prev = next
                }
            }
            nextsLookup[prev.getId()] = container
            prevsLookup[container.getId()] = prev
            lastFocusedComponent = container
        }
    }

    private fun handleMouseMoved(mouseAction: MouseAction) {
        if (mouseAction.position != lastMousePosition) {
            lastMousePosition = mouseAction.position
            container.fetchComponentByPosition(lastMousePosition)
                    .map { currComponent ->
                        if(lastHoveredComponentId == currComponent.getId()) {
                            if(lastFocusedComponent.getId() == currComponent.getId()) {
                                EventBus.emit(MouseMoved(currComponent.getId()), mouseAction)
                            }
                        } else {
                            EventBus.emit(MouseOut(lastHoveredComponentId))
                            lastHoveredComponentId = currComponent.getId()
                            EventBus.emit(MouseOver(currComponent.getId()))
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