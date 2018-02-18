package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.graphics.Box
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.shape.LineFactory

class DefaultBox(size: Size,
                 filler: TextCharacter,
                 styleSet: StyleSet,
                 boxType: BoxType,
                 private val backend: TextImage = TextImageBuilder.newBuilder()
                         .size(size)
                         .filler(filler)
                         .build())
    : Box, TextImage by backend {

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
                toPoint = Position(size.xLength - 3, 0))
                .toTextImage(horizontalChar)
        val verticalLine = LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, size.yLength - 3))
                .toTextImage(verticalChar)
        draw(horizontalLine, Position(1, 0))
        draw(horizontalLine, Position(1, size.yLength - 1))
        draw(verticalLine, Position(0, 1))
        draw(verticalLine, Position(size.xLength - 1, 1))
        setCharacterAt(Position(0, 0),
                verticalChar.withCharacter(boxType.topLeft))
        setCharacterAt(Position(size.xLength - 1, 0),
                verticalChar.withCharacter(boxType.topRight))
        setCharacterAt(Position(0, size.yLength - 1),
                verticalChar.withCharacter(boxType.bottomLeft))
        setCharacterAt(Position(size.xLength - 1, size.yLength - 1),
                verticalChar.withCharacter(boxType.bottomRight))
    }

    override fun toString() = backend.toString()
}
