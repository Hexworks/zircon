package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.internal.component.ContainerHandler
import org.codetome.zircon.internal.component.ContainerHandlerState.*
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import java.util.*

class DefaultContainerHandler(private var container: Container) : ContainerHandler {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()
    private var state = UNKNOWN

    init {
        EventBus.subscribe<MouseAction>(EventType.MouseAction, { (mouseAction) ->
            if (state == ACTIVE) {
                if (mouseAction.actionType == MouseActionType.MOUSE_MOVED) {
                    handleMouseMoved(mouseAction)
                } else if(mouseAction.actionType == MouseActionType.MOUSE_PRESSED) {
                    container.fetchComponentByPosition(mouseAction.position)
                            .map {
                                EventBus.emit(EventType.MousePressed(it.getId()), mouseAction)
                            }
                } else if(mouseAction.actionType == MouseActionType.MOUSE_RELEASED) {
                    container.fetchComponentByPosition(mouseAction.position)
                            .map {
                                EventBus.emit(EventType.MouseReleased(it.getId()), mouseAction)
                            }
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
                        EventBus.emit(EventType.MouseOut(lastHoveredComponentId))
                        lastHoveredComponentId = it.getId()
                        EventBus.emit(EventType.MouseOver(it.getId()))
                    }
        }
    }
}