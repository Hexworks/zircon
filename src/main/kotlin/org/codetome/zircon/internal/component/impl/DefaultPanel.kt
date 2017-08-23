package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Panel
import org.codetome.zircon.api.shape.FilledRectangleFactory
import org.codetome.zircon.internal.component.WrappingStrategy

class DefaultPanel(private val title: String,
                   initialSize: Size,
                   position: Position,
                   componentStyles: ComponentStyles,
                   wrappers: Iterable<WrappingStrategy> = listOf())
    : Panel, DefaultContainer(initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers) {


    init {
        FilledRectangleFactory.buildFilledRectangle(
                topLeft = Position.DEFAULT_POSITION,
                size = getEffectiveSize())
                .toTextImage(TextCharacterBuilder.newBuilder()
                        .styleSet(getComponentStyles().getCurrentStyle())
                        .build())
                .drawOnto(
                        surface = getDrawSurface(),
                        offset = getOffset())
    }

    override fun getTitle() = title
}