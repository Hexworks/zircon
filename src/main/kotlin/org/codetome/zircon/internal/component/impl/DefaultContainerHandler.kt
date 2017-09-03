package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType.*
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType.*
import org.codetome.zircon.internal.component.ContainerHandlerState.*
import org.codetome.zircon.internal.component.InternalContainerHandler
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.event.Subscription
import java.util.*

class DefaultContainerHandler(private var container: DefaultContainer) : InternalContainerHandler {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()
    private var lastFocusedComponentId = container.getId()
    private var lastFocusedComponent = Optional.empty<Component>()
    private var state = UNKNOWN
    private val subscriptions = mutableListOf<Subscription<*>>()
    private var nextsLookup = mutableMapOf<UUID, Component>(Pair(container.getId(), container))
    private var prevsLookup = mutableMapOf<UUID, Component>(Pair(container.getId(), container))

    private val keyStrokeHandlers = mapOf(
            Pair(NEXT_FOCUS_STROKE, this::focusNext),
            Pair(PREV_FOCUS_STROKE, this::focusPrevious),
            Pair(CLICK_STROKE, this::clickFocused))
            .toMap()

    override fun addComponent(component: Component) {
        container.addComponent(component)
        refreshFocusableLookup()
    }

    override fun removeComponent(component: Component) {
        container.removeComponent(component)
        refreshFocusableLookup()
    }

    override fun applyTheme(theme: Theme) {
        container.applyTheme(theme)
    }

    override fun isActive() = state == ACTIVE

    override fun activate() {
        state = ACTIVE
        subscriptions.add(EventBus.subscribe<Input>(EventType.Input, { (input) ->

            keyStrokeHandlers[input]?.invoke()

            if(input is KeyStroke) {
                EventBus.emit(EventType.KeyPressed, input)
            }

            if (input is MouseAction) {
                when (input.actionType) {
                    MOUSE_MOVED -> handleMouseMoved(input)

                    MOUSE_PRESSED -> container
                            .fetchComponentByPosition(input.position)
                            .map {
                                println("pressed on ${it.getId()}")
                                EventBus.emit(EventType.MousePressed(it.getId()), input)
                            }

                    MOUSE_RELEASED -> container
                            .fetchComponentByPosition(input.position)
                            .map { EventBus.emit(EventType.MouseReleased(it.getId()), input) }
                    else -> {
                    }
                }
            }
        }))
        subscriptions.add(EventBus.subscribe<UUID>(EventType.RequestFocusAt, { (componentId) ->
            focusComponent(componentId)
        }))
    }

    override fun deactivate() {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        subscriptions.clear()
        state = DEACTIVATED
    }

    override fun transformComponentsToLayers(): List<Layer> {
        return container.transformToLayers()
    }

    private fun clickFocused() {
        lastFocusedComponent.map {
            EventBus.emit(
                    type = EventType.MouseReleased(it.getId()),
                    data = MouseAction(MOUSE_RELEASED, 1, it.getPosition()))
        }
    }

    private fun focusComponent(componentId: UUID) {
        lastFocusedComponentId = prevsLookup[componentId]!!.getId()
        focusNext()
    }

    private fun focusNext() {
        nextsLookup[lastFocusedComponentId]?.let { next ->
            lastFocusedComponentId = next.getId()
            lastFocusedComponent = Optional.of(next)
            prevsLookup[next.getId()]?.takeFocus()
            next.giveFocus()
        }
    }

    private fun focusPrevious() {
        prevsLookup[lastFocusedComponentId]?.let { prev ->
            lastFocusedComponentId = prev.getId()
            lastFocusedComponent = Optional.of(prev)
            nextsLookup[prev.getId()]?.takeFocus()
            prev.giveFocus()
        }
    }

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
            lastFocusedComponentId = container.getId()
        }
    }

    private fun handleMouseMoved(mouseAction: MouseAction) {
        if (mouseAction.position != lastMousePosition) {
            lastMousePosition = mouseAction.position
            container.fetchComponentByPosition(lastMousePosition)
                    .filter {
                        lastHoveredComponentId != it.getId()
                    }
                    .map {
                        EventBus.emit(EventType.MouseOut(lastHoveredComponentId))
                        lastHoveredComponentId = it.getId()
                        EventBus.emit(EventType.MouseOver(it.getId()))
                    }
        }
    }

    companion object {
        val NEXT_FOCUS_STROKE = KeyStroke(type = Tab)
        val PREV_FOCUS_STROKE = KeyStroke(shiftDown = true, type = ReverseTab)
        val CLICK_STROKE = KeyStroke(type = Character, character = ' ')
    }
}