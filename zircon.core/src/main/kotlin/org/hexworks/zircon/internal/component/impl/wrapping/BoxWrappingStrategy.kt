package org.hexworks.zircon.internal.component.impl.wrapping

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.WrappingStrategy

class BoxWrappingStrategy(private val boxType: BoxType,
                          private val title: Maybe<String> = Maybe.empty()) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(2, 2)

    override fun getOffset() = Position.offset1x1()

    override fun apply(tileGraphic: TileGraphic, size: Size, offset: Position, style: StyleSet) {
        BoxBuilder.newBuilder()
                .boxType(boxType)
                .size(size)
                .style(style)
                .tileset(tileGraphic.tileset())
                .build()
                .drawOnto(tileGraphic, offset)
        if (size.xLength > 4) {
            title.map { titleText ->
                val cleanText = if (titleText.length > size.xLength - 4) {
                    titleText.substring(0, size.xLength - 4)
                } else {
                    titleText
                }
                tileGraphic.setTileAt(offset.withRelativeX(1), TileBuilder.newBuilder()
                        .styleSet(style)
                        .character(boxType.connectorLeft)
                        .build())
                val pos = offset.withRelativeX(2)
                (0 until cleanText.length).forEach { idx ->
                    tileGraphic.setTileAt(
                            position = pos.withRelativeX(idx),
                            tile = TileBuilder.newBuilder()
                                    .styleSet(style)
                                    .character(cleanText[idx])
                                    .build())
                }
                tileGraphic.setTileAt(
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
