package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.builder.modifier.BorderBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.api.modifier.BorderPosition.*
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.internal.component.WrappingStrategy

class BorderWrappingStrategy(private val border: Border) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(0, 0)

    override fun getOffset() = Position.defaultPosition()

    override fun apply(tileImage: TileImage, size: Size, offset: Position, style: StyleSet) {
        val drawTop = border.borderPositions.contains(TOP)
        val drawBottom = border.borderPositions.contains(BOTTOM)
        val drawLeft = border.borderPositions.contains(LEFT)
        val drawRight = border.borderPositions.contains(RIGHT)

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

        val topLeftPos = size.fetchTopLeftPosition().withRelative(offset)
        val topRightPos = size.fetchTopRightPosition().withRelative(offset)
        val bottomLeftPos = size.fetchBottomLeftPosition().withRelative(offset)
        val bottomRightPos = size.fetchBottomRightPosition().withRelative(offset)

        if (drawTop.or(drawLeft)) {
            tileImage.getTileAt(topLeftPos).map { char ->
                tileImage.setTileAt(topLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topLeftBorders)
                                .build()))
            }
        }
        if (drawTop.or(drawRight)) {
            tileImage.getTileAt(topRightPos).map { char ->
                tileImage.setTileAt(topRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topRightBorders)
                                .build()))
            }
        }
        if (drawLeft.or(drawBottom)) {
            tileImage.getTileAt(bottomLeftPos).map { char ->
                tileImage.setTileAt(bottomLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(bottomLeftBorders)
                                .build()))
            }
        }
        if (drawRight.or(drawBottom)) {
            tileImage.getTileAt(bottomRightPos).map { char ->
                tileImage.setTileAt(bottomRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(bottomRightBorders)
                                .build()))
            }
        }
        if (size.xLength > 2) {
            val horizontalLine = LineFactory.buildLine(topLeftPos, topRightPos.withRelativeX(-2))
            if (drawTop.or(drawBottom)) {
                horizontalLine.getPositions().forEach {
                    if (drawTop) {
                        val topOffset = it.withRelativeX(1).withRelative(offset)
                        tileImage.getTileAt(topOffset).map { char ->
                            tileImage.setTileAt(topOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(TOP)
                                            .build()))
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeX(1)
                                .withRelativeY(size.yLength - 1)
                        tileImage.getTileAt(bottomOffset).map { char ->
                            tileImage.setTileAt(bottomOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(BOTTOM)
                                            .build()))
                        }
                    }
                }
            }
        }
        if (size.yLength > 2) {
            val verticalLine = LineFactory.buildLine(topLeftPos, bottomLeftPos.withRelativeY(-2))
            if (drawLeft.or(drawRight)) {
                verticalLine.getPositions().forEach {
                    if (drawLeft) {
                        val leftOffset = it.withRelativeY(1).withRelative(offset)
                        tileImage.getTileAt(leftOffset).map { char ->
                            tileImage.setTileAt(leftOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(LEFT)
                                            .build()))
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeY(1)
                                .withRelativeX(size.xLength - 1)
                        tileImage.getTileAt(rightOffset).map { char ->
                            tileImage.setTileAt(rightOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(RIGHT)
                                            .build()))
                        }
                    }
                }
            }
        }
    }

    override fun remove(tileImage: TileImage, size: Size, offset: Position, style: StyleSet) {
        // TODO: FIX CAST
        size.fetchBoundingBoxPositions().forEach { pos ->
            val fixedPos = pos.withRelative(offset)
            tileImage.getTileAt(fixedPos).map { char ->
                if (char.hasBorder()) {
                    tileImage.setTileAt(fixedPos, char.withoutModifiers(*char.fetchBorderData().toTypedArray()))
                }
            }
        }
    }

    override fun isThemeNeutral() = false

}
