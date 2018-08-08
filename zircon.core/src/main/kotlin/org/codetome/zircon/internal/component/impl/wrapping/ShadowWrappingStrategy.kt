package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.Symbols
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.internal.component.WrappingStrategy

class ShadowWrappingStrategy(
        private val shadowChar: Char = DEFAULT_SHADOW_CHAR) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(1, 1)

    override fun getOffset() = Position.topLeftCorner()

    override fun apply(tileImage: TileImage, size: Size, offset: Position, style: StyleSet) {
        val tc = TileBuilder.newBuilder()
                .backgroundColor(TextColor.transparent())
                .foregroundColor(TextColor.create(100, 100, 100))
                .character(shadowChar)
                .build()
        LineFactory.buildLine(
                fromPoint = Position.create(1, 0),
                toPoint = Position.create(size.xLength - 1, 0))
                .toTileImage(tc, tileImage.tileset())
                .drawOnto(tileImage, Position.create(1, size.yLength - 1))
        LineFactory.buildLine(
                fromPoint = Position.create(0, 1),
                toPoint = Position.create(0, size.yLength - 1))
                .toTileImage(tc, tileImage.tileset())
                .drawOnto(tileImage, Position.create(size.xLength - 1, 1))
    }

    override fun isThemeNeutral() = true

    companion object {
        val DEFAULT_SHADOW_CHAR = Symbols.BLOCK_SPARSE
    }
}
