package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderPosition.BOTTOM
import org.hexworks.zircon.api.modifier.BorderPosition.LEFT
import org.hexworks.zircon.api.modifier.BorderPosition.RIGHT
import org.hexworks.zircon.api.modifier.BorderPosition.TOP
import org.hexworks.zircon.api.shape.LineFactory

data class BorderDecorationRenderer(
    val border: Border,
    val renderingMode: RenderingMode = RenderingMode.NON_INTERACTIVE
) : ComponentDecorationRenderer {

    override val offset = Position.defaultPosition()

    override val occupiedSize = Size.create(0, 0)

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {

        val color = context.fetchStyleFor(renderingMode).backgroundColor

        val drawTop = border.borderPositions.contains(TOP)
        val drawBottom = border.borderPositions.contains(BOTTOM)
        val drawLeft = border.borderPositions.contains(LEFT)
        val drawRight = border.borderPositions.contains(RIGHT)
        val size = tileGraphics.size

        val topLeftBorders = mutableSetOf<BorderPosition>()
        val topRightBorders = mutableSetOf<BorderPosition>()
        val bottomLeftBorders = mutableSetOf<BorderPosition>()
        val bottomRightBorders = mutableSetOf<BorderPosition>()

        if (drawTop) {
            topLeftBorders.add(TOP)
            topRightBorders.add(TOP)
        }
        if (drawBottom) {
            bottomLeftBorders.add(BOTTOM)
            bottomRightBorders.add(BOTTOM)
        }
        if (drawLeft) {
            topLeftBorders.add(LEFT)
            bottomLeftBorders.add(LEFT)
        }
        if (drawRight) {
            topRightBorders.add(RIGHT)
            bottomRightBorders.add(RIGHT)
        }

        val topLeftPos = size.fetchTopLeftPosition()
        val topRightPos = size.fetchTopRightPosition()
        val bottomLeftPos = size.fetchBottomLeftPosition()
        val bottomRightPos = size.fetchBottomRightPosition()

        if (drawTop.or(drawLeft)) {
            tileGraphics.getTileAtOrNull(topLeftPos)?.let { char: Tile ->
                tileGraphics.draw(
                    char
                        .withModifiers(
                            BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderColor(color)
                                .withBorderPositions(topLeftBorders)
                                .build()
                        ), topLeftPos
                )
            }
        }
        if (drawTop.or(drawRight)) {
            tileGraphics.getTileAtOrNull(topRightPos)?.let { char ->
                tileGraphics.draw(
                    char
                        .withModifiers(
                            BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderColor(color)
                                .withBorderPositions(topRightBorders)
                                .build()
                        ), topRightPos
                )
            }
        }
        if (drawLeft.or(drawBottom)) {
            tileGraphics.getTileAtOrNull(bottomLeftPos)?.let { char ->
                tileGraphics.draw(
                    char
                        .withModifiers(
                            BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderColor(color)
                                .withBorderPositions(bottomLeftBorders)
                                .build()
                        ), bottomLeftPos
                )
            }
        }
        if (drawRight.or(drawBottom)) {
            tileGraphics.getTileAtOrNull(bottomRightPos)?.let { char ->
                tileGraphics.draw(
                    char
                        .withModifiers(
                            BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderColor(color)
                                .withBorderPositions(bottomRightBorders)
                                .build()
                        ), bottomRightPos
                )
            }
        }
        if (size.width > 2) {
            val horizontalLine = LineFactory.buildLine(topLeftPos, topRightPos.withRelativeX(-2))
            if (drawTop.or(drawBottom)) {
                horizontalLine.positions.forEach {
                    if (drawTop) {
                        val topOffset = it.withRelativeX(1)
                        tileGraphics.getTileAtOrNull(topOffset)?.let { char ->
                            tileGraphics.draw(
                                char
                                    .withModifiers(
                                        BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderColor(color)
                                            .withBorderPositions(TOP)
                                            .build()
                                    ), topOffset
                            )
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeX(1)
                            .withRelativeY(size.height - 1)
                        tileGraphics.getTileAtOrNull(bottomOffset)?.let { char ->
                            tileGraphics.draw(
                                char
                                    .withModifiers(
                                        BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderColor(color)
                                            .withBorderPositions(BOTTOM)
                                            .build()
                                    ), bottomOffset
                            )
                        }
                    }
                }
            }
        }
        if (size.height > 2) {
            val verticalLine = LineFactory.buildLine(topLeftPos, bottomLeftPos.withRelativeY(-2))
            if (drawLeft.or(drawRight)) {
                verticalLine.positions.forEach {
                    if (drawLeft) {
                        val leftOffset = it.withRelativeY(1)
                        tileGraphics.getTileAtOrNull(leftOffset)?.let { char ->
                            tileGraphics.draw(
                                char
                                    .withModifiers(
                                        BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderColor(color)
                                            .withBorderPositions(LEFT)
                                            .build()
                                    ), leftOffset
                            )
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeY(1)
                            .withRelativeX(size.width - 1)
                        tileGraphics.getTileAtOrNull(rightOffset)?.let { char ->
                            tileGraphics.draw(
                                char
                                    .withModifiers(
                                        BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderColor(color)
                                            .withBorderPositions(RIGHT)
                                            .build()
                                    ), rightOffset
                            )
                        }
                    }
                }
            }
        }
    }
}
