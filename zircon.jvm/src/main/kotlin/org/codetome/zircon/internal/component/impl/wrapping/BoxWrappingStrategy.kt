package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.graphics.BoxType
import java.util.*

class BoxWrappingStrategy(private val boxType: BoxType,
                          private val title: Optional<String> = Optional.empty()) : WrappingStrategy {

    override fun getOccupiedSize() = Size.of(2, 2)

    override fun getOffset() = Position.OFFSET_1x1

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
        BoxBuilder.newBuilder()
                .boxType(boxType)
                .size(size)
                .style(style)
                .build()
                .drawOnto(textImage, offset)
        if (size.xLength > 4) {
            title.map { titleText ->
                val cleanText = if (titleText.length > size.xLength - 4) {
                    titleText.substring(0, size.xLength - 4)
                } else {
                    titleText
                }
                textImage.setCharacterAt(offset.withRelativeX(1), boxType.connectorLeft)
                textImage.putText(cleanText, offset.withRelativeX(2))
                textImage.setCharacterAt(offset.withRelativeX(2 + cleanText.length), boxType.connectorRight)
            }
        }
    }

    override fun isThemeNeutral() = false
}
