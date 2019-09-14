package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.shape.LineFactory

data class BorderDecorationRenderer(val border: Border) : ComponentDecorationRenderer {

    override val offset = Position.defaultPosition()

    override val occupiedSize = Size.create(0, 0)

    override fun render(tileGraphics: SubTileGraphics, context: ComponentDecorationRenderContext) {
        val drawTop = border.borderPositions.contains(BorderPosition.TOP)
        val drawBottom = border.borderPositions.contains(BorderPosition.BOTTOM)
        val drawLeft = border.borderPositions.contains(BorderPosition.LEFT)
        val drawRight = border.borderPositions.contains(BorderPosition.RIGHT)
        val size = tileGraphics.size

        val topLeftBorders = mutableSetOf<BorderPosition>()
        val topRightBorders = mutableSetOf<BorderPosition>()
        val bottomLeftBorders = mutableSetOf<BorderPosition>()
        val bottomRightBorders = mutableSetOf<BorderPosition>()

        if (drawTop) {
            topLeftBorders.add(BorderPosition.TOP)
            topRightBorders.add(BorderPosition.TOP)
        }
        if (drawBottom) {
            bottomLeftBorders.add(BorderPosition.BOTTOM)
            bottomRightBorders.add(BorderPosition.BOTTOM)
        }
        if (drawLeft) {
            topLeftBorders.add(BorderPosition.LEFT)
            bottomLeftBorders.add(BorderPosition.LEFT)
        }
        if (drawRight) {
            topRightBorders.add(BorderPosition.RIGHT)
            bottomRightBorders.add(BorderPosition.RIGHT)
        }

        val topLeftPos = size.fetchTopLeftPosition()
        val topRightPos = size.fetchTopRightPosition()
        val bottomLeftPos = size.fetchBottomLeftPosition()
        val bottomRightPos = size.fetchBottomRightPosition()

        if (drawTop.or(drawLeft)) {
            tileGraphics.getTileAt(topLeftPos).map { char: Tile ->
                tileGraphics.setTileAt(topLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderPositions(topLeftBorders)
                                .build()))
            }
        }
        if (drawTop.or(drawRight)) {
            tileGraphics.getTileAt(topRightPos).map { char ->
                tileGraphics.setTileAt(topRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderPositions(topRightBorders)
                                .build()))
            }
        }
        if (drawLeft.or(drawBottom)) {
            tileGraphics.getTileAt(bottomLeftPos).map { char ->
                tileGraphics.setTileAt(bottomLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderPositions(bottomLeftBorders)
                                .build()))
            }
        }
        if (drawRight.or(drawBottom)) {
            tileGraphics.getTileAt(bottomRightPos).map { char ->
                tileGraphics.setTileAt(bottomRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .withBorderType(border.borderType)
                                .withBorderPositions(bottomRightBorders)
                                .build()))
            }
        }
        if (size.width > 2) {
            val horizontalLine = LineFactory.buildLine(topLeftPos, topRightPos.withRelativeX(-2))
            if (drawTop.or(drawBottom)) {
                horizontalLine.positions().forEach {
                    if (drawTop) {
                        val topOffset = it.withRelativeX(1)
                        tileGraphics.getTileAt(topOffset).map { char ->
                            tileGraphics.setTileAt(topOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderPositions(BorderPosition.TOP)
                                            .build()))
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeX(1)
                                .withRelativeY(size.height - 1)
                        tileGraphics.getTileAt(bottomOffset).map { char ->
                            tileGraphics.setTileAt(bottomOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderPositions(BorderPosition.BOTTOM)
                                            .build()))
                        }
                    }
                }
            }
        }
        if (size.height > 2) {
            val verticalLine = LineFactory.buildLine(topLeftPos, bottomLeftPos.withRelativeY(-2))
            if (drawLeft.or(drawRight)) {
                verticalLine.positions().forEach {
                    if (drawLeft) {
                        val leftOffset = it.withRelativeY(1)
                        tileGraphics.getTileAt(leftOffset).map { char ->
                            tileGraphics.setTileAt(leftOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderPositions(BorderPosition.LEFT)
                                            .build()))
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeY(1)
                                .withRelativeX(size.width - 1)
                        tileGraphics.getTileAt(rightOffset).map { char ->
                            tileGraphics.setTileAt(rightOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .withBorderType(border.borderType)
                                            .withBorderPositions(BorderPosition.RIGHT)
                                            .build()))
                        }
                    }
                }
            }
        }
    }
}
