package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Modifiers.BorderPosition
import org.codetome.zircon.api.Modifiers.BorderPosition.*
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.internal.BuiltInModifiers.Border
import org.codetome.zircon.internal.component.WrappingStrategy

class BorderWrappingStrategy(private val border: Border) : WrappingStrategy {

    override fun getOccupiedSize() = Size.of(0, 0)

    override fun getOffset() = Position.DEFAULT_POSITION

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
        val drawTop = border.borderPositions.contains(TOP)
        val drawBottom = border.borderPositions.contains(BOTTOM)
        val drawLeft = border.borderPositions.contains(LEFT)
        val drawRight = border.borderPositions.contains(RIGHT)

        val topLeftBorders = mutableListOf<BorderPosition>()
        val topRightBorders = mutableListOf<BorderPosition>()
        val bottomLeftBorders = mutableListOf<BorderPosition>()
        val bottomRightBorders = mutableListOf<BorderPosition>()

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
                        .withModifiers(Modifiers.BORDER.create(
                                borderType = border.borderType,
                                borderPositions = *topLeftBorders.toTypedArray())))
            }
        }
        if (drawTop.or(drawRight)) {
            textImage.getCharacterAt(topRightPos).map { char ->
                textImage.setCharacterAt(topRightPos, char
                        .withModifiers(Modifiers.BORDER.create(
                                borderType = border.borderType,
                                borderPositions = *topRightBorders.toTypedArray())))
            }
        }
        if (drawLeft.or(drawBottom)) {
            textImage.getCharacterAt(bottomLeftPos).map { char ->
                textImage.setCharacterAt(bottomLeftPos, char
                        .withModifiers(Modifiers.BORDER.create(
                                borderType = border.borderType,
                                borderPositions = *bottomLeftBorders.toTypedArray())))
            }
        }
        if (drawRight.or(drawBottom)) {
            textImage.getCharacterAt(bottomRightPos).map { char ->
                textImage.setCharacterAt(bottomRightPos, char
                        .withModifiers(Modifiers.BORDER.create(
                                borderType = border.borderType,
                                borderPositions = *bottomRightBorders.toTypedArray())))
            }
        }
        if (size.columns > 2) {
            val horizontalLine = LineFactory.buildLine(topLeftPos, topRightPos.withRelativeColumn(-2))
            if (drawTop.or(drawBottom)) {
                horizontalLine.getPositions().forEach {
                    if (drawTop) {
                        val topOffset = it.withRelativeColumn(1).withRelative(offset)
                        textImage.getCharacterAt(topOffset).map { char ->
                            textImage.setCharacterAt(topOffset, char
                                    .withModifiers(Modifiers.BORDER.create(border.borderType, TOP)))
                        }
                    }
                    if (drawBottom) {
                        val bottomOffset = it.withRelativeColumn(1)
                                .withRelativeRow(size.rows - 1)
                        textImage.getCharacterAt(bottomOffset).map { char ->
                            textImage.setCharacterAt(bottomOffset, char
                                    .withModifiers(Modifiers.BORDER.create(border.borderType, BOTTOM)))
                        }
                    }
                }
            }
        }
        if (size.rows > 2) {
            val verticalLine = LineFactory.buildLine(topLeftPos, bottomLeftPos.withRelativeRow(-2))
            if (drawLeft.or(drawRight)) {
                verticalLine.getPositions().forEach {
                    if (drawLeft) {
                        val leftOffset = it.withRelativeRow(1).withRelative(offset)
                        textImage.getCharacterAt(leftOffset).map { char ->
                            textImage.setCharacterAt(leftOffset, char
                                    .withModifiers(Modifiers.BORDER.create(border.borderType, LEFT)))
                        }
                    }
                    if (drawRight) {
                        val rightOffset = it.withRelativeRow(1)
                                .withRelativeColumn(size.columns - 1)
                        textImage.getCharacterAt(rightOffset).map { char ->
                            textImage.setCharacterAt(rightOffset, char
                                    .withModifiers(Modifiers.BORDER.create(border.borderType, RIGHT)))
                        }
                    }
                }
            }
        }
    }

    override fun remove(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
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