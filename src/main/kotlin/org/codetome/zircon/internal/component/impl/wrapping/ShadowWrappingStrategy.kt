package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.Symbols
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.internal.component.WrappingStrategy

class ShadowWrappingStrategy(private val shadowChar: Char = DEFAULT_SHADOW_CHAR) : WrappingStrategy {

    override fun getOccupiedSize() = Size.of(1, 1)

    override fun getOffset() = Position.TOP_LEFT_CORNER

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
        val tc = TextCharacterBuilder.newBuilder()
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .foregroundColor(TextColorFactory.fromString("#555555"))
                .character(shadowChar)
                .build()
        LineFactory.buildLine(
                fromPoint = Position.of(1, 0),
                toPoint = Position.of(size.columns - 1, 0))
                .toTextImage(tc)
                .drawOnto(textImage, Position.of(1, size.rows - 1))
        LineFactory.buildLine(
                fromPoint = Position.of(0, 1),
                toPoint = Position.of(0, size.rows - 1))
                .toTextImage(tc)
                .drawOnto(textImage, Position.of(size.columns - 1, 1))
    }

    override fun isThemeNeutral() = true

    companion object {
        val DEFAULT_SHADOW_CHAR = Symbols.BLOCK_SPARSE
    }
}