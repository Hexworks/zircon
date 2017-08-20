package org.codetome.zircon.component.impl

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.behavior.DrawSurface
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.component.Component
import org.codetome.zircon.component.ComponentStyles
import org.codetome.zircon.component.listener.MouseListener
import org.codetome.zircon.graphics.image.TextImage

class DefaultComponent private constructor(private val backend: TextImage,
                                           private val position: Position,
                                           private val componentStyles: ComponentStyles)
    : Component, Drawable by backend {

    constructor(initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles) : this(
            backend = TextImageBuilder.newBuilder()
                    .filler(TextCharacterBuilder.newBuilder()
                            .styleSet(componentStyles.defaultStyle)
                            .build())
                    .size(initialSize)
                    .build(),
            position = position,
            componentStyles = componentStyles)

    init {
        backend.setStyleFrom(componentStyles.defaultStyle)
    }

    override fun getPosition() = position

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(backend, position)
    }

    override fun drawOnto(destination: DrawSurface,
                          startRowIndex: Int,
                          rows: Int,
                          startColumnIndex: Int,
                          columns: Int,
                          destinationRowOffset: Int,
                          destinationColumnOffset: Int) {
        backend.drawOnto(destination, startRowIndex, rows, startColumnIndex, columns,
                destinationRowOffset, destinationColumnOffset)
    }

    override fun addMouseListener(mouseListener: MouseListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setComponentStyles(componentStyles: ComponentStyles) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}