package org.codetome.zircon.graphics.box

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.graphics.image.TextImage
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.graphics.style.StyleSet

class DefaultBox private constructor(size: Size,
                                     styleSet: StyleSet,
                                     boxType: BoxType,
                                     private val backend: TextImage) : Box, TextImage by backend {

    constructor(size: Size,
                filler: TextCharacter,
                styleSet: StyleSet,
                boxType: BoxType) : this(
            size = size,
            backend = TextImageBuilder.newBuilder()
                    .size(size)
                    .filler(filler)
                    .build(),
            styleSet = styleSet,
            boxType = boxType)

    init {
        setStyleFrom(styleSet)
        val verticalChar = TextCharacterBuilder.newBuilder()
                .styleSet(styleSet)
                .character(boxType.vertical)
                .build()
        val horizontalChar = verticalChar
                .withCharacter(boxType.horizontal)

        val horizontalLine = LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(size.columns - 3, 0))
                .toTextImage(horizontalChar)
        val verticalLine = LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, size.rows - 3))
                .toTextImage(verticalChar)
        draw(horizontalLine, Position(1, 0))
        draw(horizontalLine, Position(1, size.rows - 1))
        draw(verticalLine, Position(0, 1))
        draw(verticalLine, Position(size.columns - 1, 1))
        setCharacterAt(Position(0, 0),
                verticalChar.withCharacter(boxType.topLeft))
        setCharacterAt(Position(size.columns - 1, 0),
                verticalChar.withCharacter(boxType.topRight))
        setCharacterAt(Position(0, size.rows - 1),
                verticalChar.withCharacter(boxType.bottomLeft))
        setCharacterAt(Position(size.columns - 1, size.rows - 1),
                verticalChar.withCharacter(boxType.bottomRight))
    }

    override fun toString() = backend.toString()
}