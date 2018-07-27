package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.builder.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.api.modifier.BorderPosition.*
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.internal.component.WrappingStrategy

class BorderWrappingStrategy(private val border: Border) : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(0, 0)

    override fun getOffset() = Position.defaultPosition()

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
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
            textImage.getCharacterAt(topLeftPos).map { char ->
                textImage.setCharacterAt(topLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topLeftBorders)
                                .build()))
            }
        }
        if (drawTop.or(drawRight)) {
            textImage.getCharacterAt(topRightPos).map { char ->
                textImage.setCharacterAt(topRightPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(topRightBorders)
                                .build()))
            }
        }
        if (drawLeft.or(drawBottom)) {
            textImage.getCharacterAt(bottomLeftPos).map { char ->
                textImage.setCharacterAt(bottomLeftPos, char
                        .withModifiers(BorderBuilder.newBuilder()
                                .borderType(border.borderType)
                                .borderPositions(bottomLeftBorders)
                                .build()))
            }
        }
        if (drawRight.or(drawBottom)) {
            textImage.getCharacterAt(bottomRightPos).map { char ->
                textImage.setCharacterAt(bottomRightPos, char
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
                        textImage.getCharacterAt(topOffset).map { char ->
                            textImage.setCharacterAt(topOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(TOP)
                                            .build()))
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeX(1)
                                .withRelativeY(size.yLength - 1)
                        textImage.getCharacterAt(bottomOffset).map { char ->
                            textImage.setCharacterAt(bottomOffset, char
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
                        textImage.getCharacterAt(leftOffset).map { char ->
                            textImage.setCharacterAt(leftOffset, char
                                    .withModifiers(BorderBuilder.newBuilder()
                                            .borderType(border.borderType)
                                            .borderPositions(LEFT)
                                            .build()))
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeY(1)
                                .withRelativeX(size.xLength - 1)
                        textImage.getCharacterAt(rightOffset).map { char ->
                            textImage.setCharacterAt(rightOffset, char
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

    override fun remove(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
        // TODO: FIX CAST
        size.fetchBoundingBoxPositions().forEach { pos ->
            val fixedPos = pos.withRelative(offset)
            textImage.getCharacterAt(fixedPos).map { char ->
                if (char.hasBorder()) {
                    textImage.setCharacterAt(fixedPos, char.withoutModifiers(*char.fetchBorderData().toTypedArray()))
                }
            }
        }
    }

    override fun isThemeNeutral() = false

}
