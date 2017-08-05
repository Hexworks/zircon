package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.Size

/**
 * This interface exposes methods for translating abstract lines, triangles and rectangles to discreet
 * points on a grid.
 */
interface ShapeRenderer {

    /**
     * Draws a line from a specified position to a specified position, using a supplied [TextCharacter].
     * The current foreground color, background color and modifiers of this [TextGraphics] will not be used
     * and will not be modified by this call.
     */
    fun drawLine(fromPoint: Position, toPoint: Position, character: TextCharacter)

    /**
     * Draws the outline of a triangle on the screen, using a supplied character.
     * The triangle will begin at p1, go through p2 and then p3 and then back to p1.
     * The current foreground color, background color and modifiers of this
     * [TextGraphics] will not be used and will not be modified by this call.
     */
    fun drawTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter)

    /**
     * Draws a filled triangle, using a supplied character. The triangle will begin at p1, go
     * through p2 and then p3 and then back to p1. The current foreground color, background color
     * and modifiers of this [TextGraphics] will not be used and will not be modified by this call.
     */
    fun fillTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter)

    /**
     * Draws the outline of a rectangle with a particular TextCharacter, ignoring the current
     * colors and modifiers of this TextGraphics.
     *
     * For example, calling createRectangle with size being the size of the terminal and top-left
     * value being the terminals top-left (0x0) corner will draw a border around the terminal.
     *
     * The current foreground color, background color and modifiers will not be modified by this call.
     */
    fun drawRectangle(topLeft: Position, size: Size, character: TextCharacter)

    /**
     * Takes a rectangle and fills it using a particular TextCharacter, ignoring the current
     * colors and modifiers of this TextGraphics. The topLeft coordinate is inclusive.
     *
     * For example, calling createFilledRectangle with size being the size of the terminal and top-left value being the terminals
     * top-left (0x0) corner will fill the entire terminal with this character.
     *
     * The current foreground color, background color and modifiers will not be modified by this call.
     */
    fun fillRectangle(topLeft: Position, size: Size, character: TextCharacter)
}
