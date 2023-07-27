package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.box
import org.hexworks.zircon.api.component.renderer.*
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.impl.DrawWindow

data class BoxDecorationRenderer(
    val boxType: BoxType = BoxType.SINGLE,
    private val titleProperty: Property<String> = "".toProperty(),
    private val renderingMode: RenderingMode = NON_INTERACTIVE,
    private val titleAlignment: Alignment = Alignment.TOP_LEFT
) : ComponentDecorationRenderer {

    override val offset = Position.offset1x1()

    override val occupiedSize = Size.create(2, 2)

    val title: String by titleProperty.asDelegate()

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val finalTitle = if (context.component is TitleOverride) {
            context.component.title
        } else titleProperty.value
        val size = drawWindow.size
        val style = context.fetchStyleFor(renderingMode)
        val boxType = this.boxType
        drawWindow.draw(
            box {
                this.boxType = boxType
                this.size = size
                this.style = style
                tileset = context.component.tileset
            }
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
                drawWindow.draw(
                    characterTile {
                        styleSet = style
                        character = boxType.connectorLeft
                    }, Position.create(1 + titleOffsetX, titleOffsetY)
                )
                val pos = Position.create(2 + titleOffsetX, titleOffsetY)
                (cleanText.indices).forEach { idx ->
                    drawWindow.draw(
                        tile = characterTile {
                            styleSet = style
                            character = cleanText[idx]
                        },
                        drawPosition = pos.withRelativeX(idx)
                    )
                }
                drawWindow.draw(
                    tile = characterTile {
                        styleSet = style
                        character = boxType.connectorRight
                    },
                    drawPosition = Position.create(2 + titleOffsetX + cleanText.length, titleOffsetY)
                )
            }
        }
    }
}
