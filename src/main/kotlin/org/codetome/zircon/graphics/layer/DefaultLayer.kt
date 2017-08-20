package org.codetome.zircon.graphics.layer

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.graphics.image.TextImage
import java.awt.Point
import java.awt.Rectangle

class DefaultLayer private constructor(offset: Position, private val textImage: TextImage)
    : Layer, TextImage by textImage {

    constructor(size: Size,
                filler: TextCharacter,
                offset: Position)
            : this(
            offset = offset,
            textImage = TextImageBuilder.newBuilder()
                    .size(size)
                    .filler(filler)
                    .build())

    private var position: Position
    private var rect: Rectangle

    init {
        this.position = offset
        this.rect = refreshRect()
    }

    override fun getPosition() = position

    override fun moveTo(position: Position) {
        this.position = position
        this.rect = refreshRect()
    }

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getPosition().column,
                    boundable.getPosition().row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(Point(position.column, position.row))
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    position.column,
                    position.row,
                    boundable.getBoundableSize().columns,
                    boundable.getBoundableSize().rows))

    override fun getCharacterAt(position: Position) = textImage.getCharacterAt(position - this.position)

    override fun setCharacterAt(position: Position, character: TextCharacter): Boolean {
        return textImage.setCharacterAt(position - this.position, character)
    }

    private fun refreshRect(): Rectangle {
        return Rectangle(position.column, position.row, getBoundableSize().columns, getBoundableSize().rows)
    }
}