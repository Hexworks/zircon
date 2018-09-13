package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.SubTileGraphics

class BoxDecorationRenderer(private val boxType: BoxType = BoxType.SINGLE) : ComponentDecorationRenderer {

    override val offset: Position = Position.offset1x1()

    override val occupiedSize: Size = Size.create(2, 2)

    override fun render(tileGraphics: SubTileGraphics, context: ComponentDecorationRenderContext) {
        val box = BoxBuilder.newBuilder()
                .boxType(boxType)
                .size(tileGraphics.size())
                .style(context.component.componentStyleSet().getCurrentStyle())
                .tileset(context.component.tileset())
                .build()
        box.drawOnto(tileGraphics)
    }
}
