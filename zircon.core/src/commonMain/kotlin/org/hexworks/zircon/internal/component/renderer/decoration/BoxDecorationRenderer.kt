package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.component.renderer.*
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.component.renderer.isRight
import org.hexworks.zircon.api.component.renderer.isTop
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.TileGraphics

data class BoxDecorationRenderer(
    val boxType: BoxType = BoxType.SINGLE,
    private val titleProperty: Property<String> = "".toProperty(),
    private val renderingMode: RenderingMode = NON_INTERACTIVE,
    private val titleAlignment: Alignment = Alignment.TOP_LEFT
) : ComponentDecorationRenderer {

    override val offset = Position.offset1x1()

    override val occupiedSize = Size.create(2, 2)

    val title: String by titleProperty.asDelegate()

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {
        val finalTitle = if (context.component is TitleOverride) {
            context.component.title
        } else titleProperty.value
        val size = tileGraphics.size
        val style = context.fetchStyleFor(renderingMode)
        tileGraphics.draw(
            BoxBuilder.newBuilder()
                .withBoxType(boxType)
                .withSize(size)
                .withStyle(style)
                .withTileset(context.component.tileset)
                .build()
        )
        if (size.width > 4) {
            if (finalTitle.isNotBlank()) {
                val cleanText = if (finalTitle.length > size.width - 4) {
                    finalTitle.substring(0, size.width - 4)
                } else {
                    finalTitle
                }
                val titleOffsetX = when {
                    titleAlignment.isLeft() -> 0
                    titleAlignment.isRight() -> size.width - cleanText.length - 4
                    titleAlignment.isCenter() -> (size.width - cleanText.length - 4) / 2
                    else -> throw IllegalStateException("unreachable")
                }
                val titleOffsetY = when {
                    titleAlignment.isTop() -> 0
                    titleAlignment.isBottom() -> size.height - 1
                    else -> throw IllegalStateException("unreachable")
                }
                tileGraphics.draw(
                    TileBuilder.newBuilder()
                        .withStyleSet(style)
                        .withCharacter(boxType.connectorLeft)
                        .build(), Position.create(1 + titleOffsetX, titleOffsetY)
                )
                val pos = Position.create(2 + titleOffsetX, titleOffsetY)
                (cleanText.indices).forEach { idx ->
                    tileGraphics.draw(
                        tile = TileBuilder.newBuilder()
                            .withStyleSet(style)
                            .withCharacter(cleanText[idx])
                            .build(),
                        drawPosition = pos.withRelativeX(idx)
                    )
                }
                tileGraphics.draw(
                    tile = TileBuilder.newBuilder()
                        .withStyleSet(style)
                        .withCharacter(boxType.connectorRight)
                        .build(),
                    drawPosition = Position.create(2 + titleOffsetX + cleanText.length, titleOffsetY)
                )
            }
        }
    }
}
