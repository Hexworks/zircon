package org.hexworks.zircon.internal.component.impl.wrapping

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.internal.component.WrappingStrategy

class BorderWrappingStrategy(private val border: Border) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(0, 0)

    override fun getOffset() = Position.defaultPosition()

    override fun apply(tileGraphic: TileGraphic, size: Size, offset: Position, style: StyleSet) {
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
            tileGraphic.getTileAt(topLeftPos).map { char ->
                tileGraphic.setTileAt(topLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topLeftBorders)
                                .build()))
            }
        }
        if (drawTop.or(drawRight)) {
            tileGraphic.getTileAt(topRightPos).map { char ->
                tileGraphic.setTileAt(topRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topRightBorders)
                                .build()))
            }
        }
        if (drawLeft.or(drawBottom)) {
            tileGraphic.getTileAt(bottomLeftPos).map { char ->
                tileGraphic.setTileAt(bottomLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(bottomLeftBorders)
                                .build()))
            }
        }
        if (drawRight.or(drawBottom)) {
            tileGraphic.getTileAt(bottomRightPos).map { char ->
                tileGraphic.setTileAt(bottomRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(bottomRightBorders)
                                .build()))
            }
        }
        if (size.xLength > 2) {
            val horizontalLine = LineFactory.buildLine(topLeftPos, topRightPos.withRelativeX(-2))
            if (drawTop.or(drawBottom)) {
                horizontalLine.positions().forEach {
                    if (drawTop) {
                        val topOffset = it.withRelativeX(1).withRelative(offset)
                        tileGraphic.getTileAt(topOffset).map { char ->
                            tileGraphic.setTileAt(topOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(TOP)
                                            .build()))
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeX(1)
                                .withRelativeY(size.yLength - 1)
                        tileGraphic.getTileAt(bottomOffset).map { char ->
                            tileGraphic.setTileAt(bottomOffset, char
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
                verticalLine.positions().forEach {
                    if (drawLeft) {
                        val leftOffset = it.withRelativeY(1).withRelative(offset)
                        tileGraphic.getTileAt(leftOffset).map { char ->
                            tileGraphic.setTileAt(leftOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(LEFT)
                                            .build()))
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeY(1)
                                .withRelativeX(size.xLength - 1)
                        tileGraphic.getTileAt(rightOffset).map { char ->
                            tileGraphic.setTileAt(rightOffset, char
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

}
