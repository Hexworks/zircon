package org.codetome.zircon.graphics.layer

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.impl.DefaultBoundable
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.Size
import java.awt.Point
import java.awt.Rectangle
import java.util.*

class DefaultLayer private constructor(private val textImage: TextImage)
    : Layer, TextImage by textImage {

    constructor(size: Size, filler: TextCharacter, offset: Position)
            : this(
            textImage = TextImageBuilder.newBuilder()
                    .size(size)
                    .filler(filler)
                    .build()) {
        this.offset = offset
    }

    private var offset = Position.DEFAULT_POSITION

    private val rect = Rectangle(offset.column, offset.row, getBoundableSize().columns, getBoundableSize().rows)

    override fun getOffset() = offset

    override fun setOffset(offset: Position) {
        this.offset = offset
    }

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    offset.column,
                    offset.row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun containsPosition(position: Position) = position.minus(offset).let {
        rect.contains(Point(it.column, it.row))
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
}