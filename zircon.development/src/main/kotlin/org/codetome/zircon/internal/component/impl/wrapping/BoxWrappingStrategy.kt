package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.BoxBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.component.WrappingStrategy

class BoxWrappingStrategy(private val boxType: BoxType,
                          private val title: Maybe<String> = Maybe.empty()) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(2, 2)

    override fun getOffset() = Position.offset1x1()

    override fun apply(tileImage: TileImage, size: Size, offset: Position, style: StyleSet) {
        BoxBuilder.newBuilder()
                .boxType(boxType)
                .size(size)
                .style(style)
                .tileset(tileImage.tileset())
                .build()
                .drawOnto(tileImage, offset)
        if (size.xLength > 4) {
            title.map { titleText ->
                val cleanText = if (titleText.length > size.xLength - 4) {
                    titleText.substring(0, size.xLength - 4)
                } else {
                    titleText
                }
                tileImage.setTileAt(offset.withRelativeX(1), TileBuilder.newBuilder()
                        .styleSet(style)
                        .character(boxType.connectorLeft)
                        .build())
                val pos = offset.withRelativeX(2)
                (0 until cleanText.length).forEach { idx ->
                    tileImage.setTileAt(
                            position = pos.withRelativeX(idx),
                            tile = TileBuilder.newBuilder()
                                    .styleSet(style)
                                    .character(cleanText[idx])
                                    .build())
                }
                tileImage.setTileAt(
                        position = offset.withRelativeX(2 + cleanText.length),
                        tile = TileBuilder.newBuilder()
                                .styleSet(style)
                                .character(boxType.connectorRight)
                                .build())
            }
        }
    }

    override fun isThemeNeutral() = false
}
