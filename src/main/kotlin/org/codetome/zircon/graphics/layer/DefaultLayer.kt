package org.codetome.zircon.graphics.layer

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.Size
import java.awt.Point
import java.awt.Rectangle

class DefaultLayer private constructor(offset: Position, private val textImage: TextImage)
    : Layer, TextImage by textImage {

    constructor(size: Size = Size.ONE,
                filler: TextCharacter = TextCharacter.EMPTY,
                offset: Position = Position.DEFAULT_POSITION)
            : this(
            offset = offset,
            textImage = TextImageBuilder.newBuilder()
                    .size(size)
                    .filler(filler)
                    .build())

    private var offset: Position
    private var rect: Rectangle

    init {
        this.offset = offset
        this.rect = refreshRect()
    }

    override fun getOffset() = offset

    override fun setOffset(offset: Position) {
        this.offset = offset
        this.rect = refreshRect()
    }

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getOffset().column,
                    boundable.getOffset().row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(Point(position.column, position.row))
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    offset.column,
                    offset.row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun getCharacterAt(position: Position) = textImage.getCharacterAt(position - offset)

    override fun setCharacterAt(position: Position, character: TextCharacter) {
        textImage.setCharacterAt(position - offset, character)
    }

    private fun refreshRect(): Rectangle {
        return Rectangle(offset.column, offset.row, getBoundableSize().columns, getBoundableSize().rows)
    }
}