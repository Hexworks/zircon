package org.codetome.zircon.graphics.box

import org.codetome.zircon.Position
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.box.BoxConnectingMode.CONNECT
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.Size
import java.util.*

class DefaultBoxRenderer : BoxRenderer {

    override fun drawBox(textGraphics: TextGraphics,
                         topLeft: Position,
                         size: Size,
                         styleToUse: StyleSet,
                         boxType: BoxType,
                         boxConnectingMode: BoxConnectingMode) {
        val (column, row) = topLeft
        val (width, height) = size
        val lastRow = row + height - 1
        val lastColumn = column + width - 1

        mapOf(
                Pair(Position.of(column, row), boxType.topLeft),
                Pair(Position.of(lastColumn, row), boxType.topRight),
                Pair(Position.of(column, lastRow), boxType.bottomLeft),
                Pair(Position.of(lastColumn, lastRow), boxType.bottomRight))
                .plus((1..height - 2).map { i ->
                    Pair(Position.of(column, row + i), boxType.vertical)
                })
                .plus((1..height - 2).map { i ->
                    Pair(Position.of(lastColumn, row + i), boxType.vertical)
                })
                .plus((1..height - 2).map { i ->
                    Pair(Position.of(column + i, row), boxType.horizontal)
                })
                .plus((1..height - 2).map { i ->
                    Pair(Position.of(column + i, lastRow), boxType.horizontal)
                })
                .forEach { (pos, char) ->
                    textGraphics.setCharacter(pos, TextCharacterBuilder.newBuilder()
                            .character(char)
                            .styleSet(styleToUse)
                            .build())
                }
        if (CONNECT == boxConnectingMode) {
            setConnector(textGraphics, Position.of(column, row), boxType)
            setConnector(textGraphics, Position.of(lastColumn, row), boxType)
            setConnector(textGraphics, Position.of(column, lastRow), boxType)
            setConnector(textGraphics, Position.of(lastColumn, lastRow), boxType)

            (1..height - 1 - 1).forEach { i ->
                setConnector(textGraphics, Position.of(column, row + i), boxType)
                setConnector(textGraphics, Position.of(lastColumn, row + i), boxType)
            }

            (1..width - 1 - 1).forEach { i ->
                setConnector(textGraphics, Position.of(column + i, row), boxType)
                setConnector(textGraphics, Position.of(column + i, lastRow), boxType)
            }
        }
    }

    private fun setConnector(textGraphics: TextGraphics,
                             position: Position,
                             boxType: BoxType) {
        Objects.requireNonNull<Any>(textGraphics)

        val validTop = textGraphics.getCharacter(position.withRelativeRow(-1))
                .filter({ boxType.isValidTopCharacter(it) }).isPresent
        val validBottom = textGraphics.getCharacter(position.withRelativeRow(1))
                .filter({ boxType.isValidBottomCharacter(it) }).isPresent
        val validLeft = textGraphics.getCharacter(position.withRelativeColumn(-1))
                .filter({ boxType.isValidLeftCharacter(it) }).isPresent
        val validRight = textGraphics.getCharacter(position.withRelativeColumn(1))
                .filter({ boxType.isValidRightCharacter(it) }).isPresent

        val neighbourPattern = booleanArrayOf(validRight, validTop, validLeft, validBottom)
        val optChar = boxType.getCharacterByNeighborPattern(neighbourPattern)

        optChar.ifPresent({
            character ->
            textGraphics.setCharacter(position, character)
        })
    }

}
