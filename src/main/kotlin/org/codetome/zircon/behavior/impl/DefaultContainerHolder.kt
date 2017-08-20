package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.behavior.ContainerHolder
import org.codetome.zircon.component.Container
import org.codetome.zircon.event.EventBus
import org.codetome.zircon.event.EventType
import org.codetome.zircon.graphics.image.TextImage
import org.codetome.zircon.input.MouseAction
import org.codetome.zircon.input.MouseActionType
import java.util.*

class DefaultContainerHolder(private var container: Container) : ContainerHolder {

    private var lastMousePosition = Position.DEFAULT_POSITION
    private var lastHoveredComponentId = UUID.randomUUID()

    init {
        EventBus.subscribe<MouseAction>(EventType.MOUSE_ACTION, { (mouseAction) ->
            if (mouseAction.actionType == MouseActionType.MOUSE_MOVED) {
                if (mouseAction.position != lastMousePosition) {
                    lastMousePosition = mouseAction.position
                    val maybeComponent = container.fetchComponentByPosition(lastMousePosition)
                    if (maybeComponent.isPresent) {
                        maybeComponent.get().let { hoveredComponent ->
                            if (lastHoveredComponentId != hoveredComponent.getId()) {
                                EventBus.emit(EventType.HOVER, hoveredComponent.getId())
                            }
                        }
                    }
                }
            }
        })
    }

    override fun getContainer() = container

    override fun setContainer(container: Container) {
        this.container = container
    }

    override fun drawComponentsToImage(): TextImage {
        val result = TextImageBuilder.newBuilder()
                .size(container.getBoundableSize())
                .build()
        container.drawOnto(result)
        return result
    }
}