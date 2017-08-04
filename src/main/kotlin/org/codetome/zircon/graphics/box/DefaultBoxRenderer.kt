package org.codetome.zircon.graphics.box

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.box.BoxConnectingMode.CONNECT
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.terminal.TerminalSize
import java.util.*

class DefaultBoxRenderer : BoxRenderer {

    override fun drawBox(textGraphics: TextGraphics,
                         topLeft: TerminalPosition,
                         size: TerminalSize,
                         styleSet: StyleSet,
                         boxType: BoxType,
                         boxConnectingMode: BoxConnectingMode) {
        val (column, row) = topLeft
        val (width, height) = size
        val lastRow = row + height - 1
        val lastColumn = column + width - 1

        mapOf(
                Pair(TerminalPosition(column, row), boxType.topLeft),
                Pair(TerminalPosition(lastColumn, row), boxType.topRight),
                Pair(TerminalPosition(column, lastRow), boxType.bottomLeft),
                Pair(TerminalPosition(lastColumn, lastRow), boxType.bottomRight))
                .plus((1..height - 2).map { i ->
                    Pair(TerminalPosition(column, row + i), boxType.vertical)
                })
                .plus((1..height - 2).map { i ->
                    Pair(TerminalPosition(lastColumn, row + i), boxType.vertical)
                })
                .plus((1..height - 2).map { i ->
                    Pair(TerminalPosition(column + i, row), boxType.horizontal)
                })
                .plus((1..height - 2).map { i ->
                    Pair(TerminalPosition(column + i, lastRow), boxType.horizontal)
                })
                .forEach { (pos, char) ->
                    textGraphics.setCharacter(pos, TextCharacterBuilder.newBuilder()
                            .character(char)
                            .styleSet(styleSet)
                            .build())
                }
        if (CONNECT == boxConnectingMode) {
            setConnector(textGraphics, TerminalPosition(column, row), boxType)
            setConnector(textGraphics, TerminalPosition(lastColumn, row), boxType)
            setConnector(textGraphics, TerminalPosition(column, lastRow), boxType)
            setConnector(textGraphics, TerminalPosition(lastColumn, lastRow), boxType)

            (1..height - 1 - 1).forEach { i ->
                setConnector(textGraphics, TerminalPosition(column, row + i), boxType)
                setConnector(textGraphics, TerminalPosition(lastColumn, row + i), boxType)
            }

            (1..width - 1 - 1).forEach { i ->
                setConnector(textGraphics, TerminalPosition(column + i, row), boxType)
                setConnector(textGraphics, TerminalPosition(column + i, lastRow), boxType)
            }
        }
    }

    private fun setConnector(textGraphics: TextGraphics,
                             terminalPosition: TerminalPosition,
                             boxType: BoxType) {
        Objects.requireNonNull<Any>(textGraphics)

        val validTop = textGraphics.getCharacter(terminalPosition.withRelativeRow(-1))
                .filter({ boxType.isValidTopCharacter(it) }).isPresent
        val validBottom = textGraphics.getCharacter(terminalPosition.withRelativeRow(1))
                .filter({ boxType.isValidBottomCharacter(it) }).isPresent
        val validLeft = textGraphics.getCharacter(terminalPosition.withRelativeColumn(-1))
                .filter({ boxType.isValidLeftCharacter(it) }).isPresent
        val validRight = textGraphics.getCharacter(terminalPosition.withRelativeColumn(1))
                .filter({ boxType.isValidRightCharacter(it) }).isPresent

        val neighbourPattern = booleanArrayOf(validRight, validTop, validLeft, validBottom)
        val optChar = boxType.getCharacterByNeighborPattern(neighbourPattern)

        optChar.ifPresent({
            character ->
            textGraphics.setCharacter(terminalPosition, character)
        })
    }

}
