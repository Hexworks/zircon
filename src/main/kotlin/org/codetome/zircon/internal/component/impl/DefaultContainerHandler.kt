package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.internal.component.ContainerHandler
import org.codetome.zircon.internal.component.ContainerHandlerState.*
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.event.Subscription
import java.util.*

class DefaultContainerHandler(private var container: Container) : ContainerHandler {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()
    private var state = UNKNOWN
    private var subscription = Optional.empty<Subscription<*>>()

    override fun getContainer() = container

    override fun isActive() = state == ACTIVE

    override fun activate() {
        state = ACTIVE
        val subscription = EventBus.subscribe<MouseAction>(EventType.MouseAction, { (mouseAction) ->
            when {
                mouseAction.actionType == MouseActionType.MOUSE_MOVED ->
                    handleMouseMoved(mouseAction)
                mouseAction.actionType == MouseActionType.MOUSE_PRESSED ->
                    container.fetchComponentByPosition(mouseAction.position)
                            .map {
                                EventBus.emit(EventType.MousePressed(it.getId()), mouseAction)
                            }
                mouseAction.actionType == MouseActionType.MOUSE_RELEASED ->
                    container.fetchComponentByPosition(mouseAction.position)
                            .map {
                                EventBus.emit(EventType.MouseReleased(it.getId()), mouseAction)
                            }
            }
        })
        this.subscription = Optional.of(subscription)
    }


    override fun deactivate() {
        subscription.map {
            EventBus.unsubscribe(it)
        }
        state = DEACTIVATED
    }

    override fun transformComponentsToLayers(): List<Layer> {
        return (container as DefaultContainer).transformToLayers()
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
}