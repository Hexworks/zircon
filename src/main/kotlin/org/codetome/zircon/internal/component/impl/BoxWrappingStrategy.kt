package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.graphics.BoxType

class BoxWrappingStrategy(private val boxType: BoxType) : WrappingStrategy {

    override fun getOccupiedSize() = Size.of(2, 2)

    override fun getOffset() = Position.OFFSET_1x1

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
            BoxBuilder.newBuilder()
                    .boxType(boxType)
                    .size(size)
                    .style(style)
                    .build()
                    .drawOnto(textImage, offset)
    }

    override fun isThemeNeutral() = false

}