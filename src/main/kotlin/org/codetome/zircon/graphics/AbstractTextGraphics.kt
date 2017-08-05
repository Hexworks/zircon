package org.codetome.zircon.graphics

import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.shape.DefaultShapeRenderer
import org.codetome.zircon.graphics.shape.ShapeRenderer
import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.codetome.zircon.terminal.Size


/**
 * This class holds the default logic for drawing the basic text graphic as exposed by [TextGraphics].
 */
abstract class AbstractTextGraphics(
        private val shapeRenderer: DefaultShapeRenderer = DefaultShapeRenderer())
    : DefaultStyleSet(), TextGraphics, ShapeRenderer by shapeRenderer {

    init {
        shapeRenderer.setCallback(object : DefaultShapeRenderer.Callback {
            override fun onPoint(position: Position, character: TextCharacter) {
                setCharacter(position, character)
            }
        })
    }

    override fun setCharacter(position: Position, character: Char) {
        return setCharacter(position, newTextCharacter(character))
    }

    override fun fill(c: Char) {
        fillRectangle(Position.TOP_LEFT_CORNER, getSize(), c)
    }

    override fun drawLine(fromPoint: Position, toPoint: Position, character: Char) {
        return drawLine(fromPoint, toPoint, newTextCharacter(character))
    }

    override fun drawTriangle(p1: Position, p2: Position, p3: Position, character: Char) {
        return drawTriangle(p1, p2, p3, newTextCharacter(character))
    }

    override fun fillTriangle(p1: Position, p2: Position, p3: Position, character: Char) {
        return fillTriangle(p1, p2, p3, newTextCharacter(character))
    }

    override fun drawRectangle(topLeft: Position, size: Size, character: Char) {
        return drawRectangle(topLeft, size, newTextCharacter(character))
    }

    override fun fillRectangle(topLeft: Position, size: Size, character: Char) {
        return fillRectangle(topLeft, size, newTextCharacter(character))
    }

    override fun drawImage(topLeft: Position, image: TextImage) {
        drawImage(topLeft, image, Position.TOP_LEFT_CORNER, image.getSize())
    }

    override fun drawImage(
            topLeft: Position,
            image: TextImage,
            sourceImageTopLeft: Position,
            sourceImageSize: Size) {
        var topLeftTmp = topLeft
        var sourceImageTopLeftTmp = sourceImageTopLeft
        var sourceImageSizeTmp = sourceImageSize

        // If the source image position is negative, offset the whole image
        if (sourceImageTopLeftTmp.column < 0) {
            topLeftTmp = topLeftTmp.withRelativeColumn(-sourceImageTopLeftTmp.column)
            sourceImageSizeTmp = sourceImageSizeTmp.withRelativeColumns(sourceImageTopLeftTmp.column)
            sourceImageTopLeftTmp = sourceImageTopLeftTmp.withColumn(0)
        }
        if (sourceImageTopLeftTmp.row < 0) {
            topLeftTmp = topLeftTmp.withRelativeRow(-sourceImageTopLeftTmp.row)
            sourceImageSizeTmp = sourceImageSizeTmp.withRelativeRows(sourceImageTopLeftTmp.row)
            sourceImageTopLeftTmp = sourceImageTopLeftTmp.withRow(0)
        }

        // cropping specified image-subrectangle to the image itself:
        var fromRow = Math.max(sourceImageTopLeftTmp.row, 0)
        var untilRow = Math.min(sourceImageTopLeftTmp.row + sourceImageSizeTmp.rows, image.getSize().rows)
        var fromColumn = Math.max(sourceImageTopLeftTmp.column, 0)
        var untilColumn = Math.min(sourceImageTopLeftTmp.column + sourceImageSizeTmp.columns, image.getSize().columns)

        // difference between position in image and position on target:
        val diffRow = topLeftTmp.row - sourceImageTopLeftTmp.row
        val diffColumn = topLeftTmp.column - sourceImageTopLeftTmp.column

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
                        position = Position(column + diffColumn, row + diffRow),
                        character = image.getCharacterAt(Position(column, row)))
            }
        }
    }

    override fun putString(position: Position, string: String, extraModifiers: Set<Modifier>) {
        val modifierDiff = extraModifiers.minus(getActiveModifiers())
        enableModifiers(*modifierDiff.toTypedArray())
        val stringToPut = prepareStringForPut(position.column, string)
        stringToPut.forEachIndexed { i, char ->
            setCharacter(position.withRelativeColumn(i), newTextCharacter(char))
        }
        disableModifiers(*modifierDiff.toTypedArray())
    }


    override fun newTextGraphics(topLeftCorner: Position, size: Size): TextGraphics {
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
        return cleanString
    }

}
