package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.TileGraphics

data class BoxDecorationRenderer(
        val boxType: BoxType = BoxType.SINGLE,
        private val titleProperty: Property<String> = createPropertyFrom("")) : ComponentDecorationRenderer {

    override val offset = Position.offset1x1()

    override val occupiedSize = Size.create(2, 2)

    val title: String by titleProperty.asDelegate()

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {
        val size = tileGraphics.size
        val style = context.component.componentStyleSet.currentStyle()
        tileGraphics.draw(BoxBuilder.newBuilder()
                .withBoxType(boxType)
                .withSize(size)
                .withStyle(style)
                .withTileset(context.component.tileset)
                .build())
        if (size.width > 4) {
            if (titleProperty.value.isNotBlank()) {
                val cleanText = if (titleProperty.value.length > size.width - 4) {
                    titleProperty.value.substring(0, size.width - 4)
                } else {
                    titleProperty.value
                }
                tileGraphics.draw(TileBuilder.newBuilder()
                        .withStyleSet(style)
                        .withCharacter(boxType.connectorLeft)
                        .build(), Position.create(1, 0))
                val pos = Position.create(2, 0)
                (cleanText.indices).forEach { idx ->
                    tileGraphics.draw(
                            tile = TileBuilder.newBuilder()
                                    .withStyleSet(style)
                                    .withCharacter(cleanText[idx])
                                    .build(),
                            drawPosition = pos.withRelativeX(idx))
                }
                tileGraphics.draw(
                        tile = TileBuilder.newBuilder()
                                .withStyleSet(style)
                                .withCharacter(boxType.connectorRight)
                                .build(),
                        drawPosition = Position.create(2 + cleanText.length, 0))
            }
        }
    }
}
