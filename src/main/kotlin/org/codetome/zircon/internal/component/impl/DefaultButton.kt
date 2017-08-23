package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.function.Consumer

class DefaultButton private constructor(private val text: String,
                                        private val backend: DefaultComponent,
                                        private val componentStyles: ComponentStyles) : Button, Component by backend {

    constructor(text: String,
                initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles) : this(
            text = "[$text]",
            backend = DefaultComponent(
                    initialSize = initialSize,
                    position = position,
                    componentStyles = componentStyles),
            componentStyles = componentStyles)

    init {
        backend.getBackend().putText(text, Position.DEFAULT_POSITION)
        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), {
            backend.getBackend().applyStyle(componentStyles.activate())
            EventBus.emit(EventType.ComponentChange)
        })
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), {
            backend.getBackend().applyStyle(componentStyles.mouseOver())
            EventBus.emit(EventType.ComponentChange)
        })
    }

    override fun getText() = text

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }
}