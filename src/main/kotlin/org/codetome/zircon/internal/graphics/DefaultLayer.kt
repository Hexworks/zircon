package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.behavior.impl.DefaultFontOverride
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage

class DefaultLayer(size: Size,
                   filler: TextCharacter,
                   offset: Position,
                   initialFont: Font<BufferedImage>,
                   private val fontOverride: FontOverride<BufferedImage> = DefaultFontOverride(
                           initialFont = initialFont),
                   private val textImage: TextImage = TextImageBuilder.newBuilder()
                           .size(size)
                           .filler(filler)
                           .build())
    : Layer, TextImage by textImage, FontOverride<BufferedImage> by fontOverride {


    private var position: Position
    private var rect: Rectangle

    init {
        this.position = offset
        this.rect = refreshRect()
    }

    override fun fetchPositions() = getBoundableSize().fetchPositions()
            .map { it + position }
            .toSet()

    override fun getPosition() = position

    override fun moveTo(position: Position) =
            if (this.position == position) {
                false
            } else {
                this.position = position
                this.rect = refreshRect()
                true
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

    override fun getRelativeCharacterAt(position: Position) = textImage.getCharacterAt(position)

    override fun setCharacterAt(position: Position, character: TextCharacter): Boolean {
        return textImage.setCharacterAt(position - this.position, character)
    }

    override fun setRelativeCharacterAt(position: Position, character: TextCharacter): Boolean {
        return textImage.setCharacterAt(position, character)
    }

    override fun createCopy() = DefaultLayer(
            size = textImage.getBoundableSize(),
            filler = TextCharacterBuilder.EMPTY,
            offset = getPosition(),
            initialFont = getCurrentFont(),
            textImage = textImage)

    private fun refreshRect(): Rectangle {
        return Rectangle(position.column, position.row, getBoundableSize().columns, getBoundableSize().rows)
    }
}