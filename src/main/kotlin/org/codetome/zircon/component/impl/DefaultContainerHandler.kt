package org.codetome.zircon.component.impl

import org.codetome.zircon.Position
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.component.Container
import org.codetome.zircon.component.ContainerHandler
import org.codetome.zircon.component.ContainerHandlerState.*
import org.codetome.zircon.event.EventBus
import org.codetome.zircon.event.EventType
import org.codetome.zircon.graphics.image.TextImage
import org.codetome.zircon.input.MouseAction
import org.codetome.zircon.input.MouseActionType
import java.util.*

class DefaultContainerHandler(private var container: Container) : ContainerHandler {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()
    private var state = UNKNOWN

    init {
        EventBus.subscribe<MouseAction>(EventType.MOUSE_ACTION, { (mouseAction) ->
            if (state == ACTIVE) {
                if (mouseAction.actionType == MouseActionType.MOUSE_MOVED) {
                    handleMouseMoved(mouseAction)
                }
            }
        })
    }

    override fun getContainer() = container

    override fun isActive() = state == ACTIVE

    override fun activate() {
        state = ACTIVE
    }

    override fun deactivate() {
        state = DEACTIVATED
    }

    override fun drawComponentsToImage(): TextImage {
        val result = TextImageBuilder.newBuilder()
                .size(container.getBoundableSize())
                .build()
        container.drawOnto(result)
        return result
    }

    private fun handleMouseMoved(mouseAction: MouseAction) {
        if (mouseAction.position != lastMousePosition) {
            lastMousePosition = mouseAction.position
            container.fetchComponentByPosition(lastMousePosition)
                    .filter {
                        lastHoveredComponentId != it.getId()
                    }
                    .map {
                        lastHoveredComponentId = it.getId()
                        EventBus.emit(EventType.HOVER, it.getId())
                    }
        }
    }
}