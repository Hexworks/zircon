package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Label

class DefaultLabel private constructor(private val text: String,
                                       private val backend: DefaultComponent) : Label, Component by backend {

    constructor(text: String,
                initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles) : this(
            text = text,
            backend = DefaultComponent(
                    initialSize = initialSize,
                    position = position,
                    componentStyles = componentStyles))

    init {
        backend.getBackend().putText(text, Position.DEFAULT_POSITION)
    }

    override fun getText() = text
}