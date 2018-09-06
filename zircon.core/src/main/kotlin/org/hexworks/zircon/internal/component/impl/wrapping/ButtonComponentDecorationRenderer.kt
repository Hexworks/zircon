package org.hexworks.zircon.internal.component.impl.wrapping

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer

class ButtonComponentDecorationRenderer : ComponentDecorationRenderer {

    override fun getOccupiedSize() = Size.create(2, 0)

    override fun getOffset() = Position.create(1, 0)

    override fun render(tileGraphic: TileGraphics, size: Size, offset: Position, style: StyleSet) {
        tileGraphic.setTileAt(
                position = offset,
                tile = TileBuilder.newBuilder()
                        .character('[')
                        .styleSet(style)
                        .build())
        tileGraphic.setTileAt(
                position = offset.withRelativeX(size.xLength - 1),
                tile = TileBuilder.newBuilder()
                        .character(']')
                        .styleSet(style)
                        .build())
    }

}
