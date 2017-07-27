package org.codetome.zircon.graphics.impl

import org.codetome.zircon.Modifier
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.ShapeRenderer
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.screen.TabBehavior
import org.codetome.zircon.terminal.TerminalSize


/**
 * This class holds the default logic for drawing the basic text graphic as exposed by [TextGraphics].
 */
abstract class AbstractTextGraphics(
        private val shapeRenderer: DefaultShapeRenderer = DefaultShapeRenderer())
    : DefaultStyleSet(), TextGraphics, ShapeRenderer by shapeRenderer {

    private var tabBehavior = TabBehavior.DEFAULT_TAB_BEHAVIOR

    init {
        shapeRenderer.setCallback(object : DefaultShapeRenderer.Callback {
            override fun onPoint(column: Int, row: Int, character: TextCharacter) {
                setCharacter(TerminalPosition(column, row), character)
            }
        })
    }

    override fun getTabBehavior(): TabBehavior {
        return tabBehavior
    }

    override fun setTabBehavior(tabBehaviour: TabBehavior) {
        this.tabBehavior = tabBehaviour
    }

    override fun setCharacter(position: TerminalPosition, character: Char) {
        return setCharacter(position, newTextCharacter(character))
    }

    override fun fill(c: Char) {
        fillRectangle(TerminalPosition.TOP_LEFT_CORNER, getSize(), c)
    }

    override fun drawLine(fromPoint: TerminalPosition, toPoint: TerminalPosition, character: Char) {
        return drawLine(fromPoint, toPoint, newTextCharacter(character))
    }

    override fun drawTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char) {
        return drawTriangle(p1, p2, p3, newTextCharacter(character))
    }

    override fun fillTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char) {
        return fillTriangle(p1, p2, p3, newTextCharacter(character))
    }

    override fun drawRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char) {
        return drawRectangle(topLeft, size, newTextCharacter(character))
    }

    override fun fillRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char) {
        return fillRectangle(topLeft, size, newTextCharacter(character))
    }

    override fun drawImage(
            topLeft: TerminalPosition,
            image: TextImage) {
        var topLeftTemp = topLeft
        var sourceImageTopLeftTemp = TerminalPosition.TOP_LEFT_CORNER
        var sourceImageSize = image.getSize()

        // If the source image position is negative, offset the whole image
        if (sourceImageTopLeftTemp.column < 0) {
            topLeftTemp = topLeftTemp.withRelativeColumn(-sourceImageTopLeftTemp.column)
            sourceImageSize = sourceImageSize.withRelativeColumns(sourceImageTopLeftTemp.column)
            sourceImageTopLeftTemp = sourceImageTopLeftTemp.withColumn(0)
        }
        if (sourceImageTopLeftTemp.row < 0) {
            topLeftTemp = topLeftTemp.withRelativeRow(-sourceImageTopLeftTemp.row)
            sourceImageSize = sourceImageSize.withRelativeRows(sourceImageTopLeftTemp.row)
            sourceImageTopLeftTemp = sourceImageTopLeftTemp.withRow(0)
        }

        // cropping specified image-subrectangle to the image itself:
        var fromRow = Math.max(sourceImageTopLeftTemp.row, 0)
        var untilRow = Math.min(sourceImageTopLeftTemp.row + sourceImageSize.rows, image.getSize().rows)
        var fromColumn = Math.max(sourceImageTopLeftTemp.column, 0)
        var untilColumn = Math.min(sourceImageTopLeftTemp.column + sourceImageSize.columns, image.getSize().columns)

        // difference between position in image and position on target:
        val diffRow = topLeftTemp.row - sourceImageTopLeftTemp.row
        val diffColumn = topLeftTemp.column - sourceImageTopLeftTemp.column

        // top/left-crop at target(TextGraphics) rectangle: (only matters, if topLeft has a negative coordinate)
        fromRow = Math.max(fromRow, -diffRow)
        fromColumn = Math.max(fromColumn, -diffColumn)

        // bot/right-crop at target(TextGraphics) rectangle: (only matters, if topLeft has a negative coordinate)
        untilRow = Math.min(untilRow, getSize().rows - diffRow)
        untilColumn = Math.min(untilColumn, getSize().columns - diffColumn)

        if (fromRow >= untilRow || fromColumn >= untilColumn) {
            return
        }
        (fromRow..untilRow - 1).forEach { row ->
            (fromColumn..untilColumn - 1).forEach { column ->
                setCharacter(
                        position = TerminalPosition(
                                column = column + diffColumn,
                                row = row + diffRow),
                        character = image.getCharacterAt(TerminalPosition(column, row)))
            }
        }
    }

    override fun putString(position: TerminalPosition, string: String, extraModifiers: Set<Modifier>) {
        val modifierDiff = extraModifiers.minus(getActiveModifiers())
        enableModifiers(*modifierDiff.toTypedArray())
        val stringToPut = prepareStringForPut(position.column, string)
        stringToPut.forEachIndexed { i, char ->
            setCharacter(position.withRelativeColumn(i), newTextCharacter(char))
        }
        disableModifiers(*modifierDiff.toTypedArray())
    }


    override fun newTextGraphics(topLeftCorner: TerminalPosition, size: TerminalSize): TextGraphics {
        val writableArea = getSize()
        if (topLeftCorner.column + size.columns <= 0 ||
                topLeftCorner.column >= writableArea.columns ||
                topLeftCorner.row + size.rows <= 0 ||
                topLeftCorner.row >= writableArea.rows) {
            //The area selected is completely outside of this TextGraphics, so we can return a "null" object that doesn't
            //do anything because it is impossible to change anything anyway
            return NoOpTextGraphics(size)
        }
        return SubTextGraphics(this, topLeftCorner, size)
    }

    private fun newTextCharacter(character: Char): TextCharacter {
        return TextCharacter(character, getForegroundColor(), getBackgroundColor(), getActiveModifiers())
    }

    private fun prepareStringForPut(column: Int, string: String): String {
        var cleanString = string
        if (cleanString.contains("\n")) {
            cleanString = cleanString.substring(0, cleanString.indexOf("\n"))
        }
        if (cleanString.contains("\r")) {
            cleanString = cleanString.substring(0, cleanString.indexOf("\r"))
        }
        cleanString = tabBehavior.replaceTabs(cleanString, column)
        return cleanString
    }

}
