@file:Suppress("unused")

package org.codetome.zircon.graphics

import org.codetome.zircon.Modifier
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.screen.TabBehavior
import org.codetome.zircon.terminal.TerminalSize
import java.util.*


/**
 * A [TextGraphics] is a group of [TextCharacter]s which are grouped together to form a quasi-graphical
 * object (like a panel, a window, or simple shapes like rectangles).
 * This interface exposes functionality to draw text graphics on a section of a
 * [org.codetome.zircon.terminal.Terminal]. It has several implementations for the different levels,
 * including one for [org.codetome.zircon.terminal.Terminal] and one for
 * [org.codetome.zircon.screen.Screen]. They are all very similar and has a lot of graphics functionality in
 * [org.codetome.zircon.graphics.impl.AbstractTextGraphics].
 *
 * The [TextGraphics] implementation keeps a state on four things:
 *  - Foreground color
 *  - Background color
 *  - Modifiers
 *  - Tab-expanding behavior
 *
 * These can all be altered through ordinary <code>set*</code> methods, but some will be altered
 * as the result of performing one of the drawing operations.
 * See the documentation to each method for further information.
 *
 * Don't hold on to your [TextGraphics] objects for too long. Let them be GC-ed when you are done
 * with them. The reason is that not all implementations will handle the underlying terminal changing size.
 */
interface TextGraphics : StyleSet, ShapeRenderer {
    /**
     * Returns the size of the area that this text graphic can write to.
     * Any attempts of placing characters outside of this area will be silently ignored.
     */
    fun getSize(): TerminalSize

    /**
     * Returns the character at the specific position in the terminal.
     */
    fun getCharacter(position: TerminalPosition): Optional<TextCharacter>

    /**
     * Sets the character at the current position to the specified value.
     */
    fun setCharacter(position: TerminalPosition, character: Char)

    /**
     * Sets the character at the current position to the specified value, without using the current
     * colors and modifiers of this TextGraphics.
     */
    fun setCharacter(position: TerminalPosition, character: TextCharacter)

    /**
     * Retrieves the current tab behavior, which is what the [TextGraphics] will use when expanding
     * '\t' (tab) characters to spaces.
     */
    fun getTabBehavior(): TabBehavior

    /**
     * Sets the behavior to use when expanding tab characters (\t) to spaces.
     */
    fun setTabBehavior(tabBehaviour: TabBehavior)

    /**
     * Fills the entire writable area with a single character, using current foreground color,
     * background color and modifiers.
     */
    fun fill(c: Char) // TODO: correlate with TextImage.setAll

    /**
     * Creates a new [TextGraphics] of the same type as this one, using the same underlying subsystem.
     * Using this method, you need to specify a section of the current [TextGraphics] valid area
     * that this new [TextGraphics] shall be restricted to.
     * If you call `newTextGraphics(TerminalPosition.TOP_LEFT_CORNER, textGraphics.getSize())`
     * then the resulting object will be identical to this one, but having a separated state for colors,
     * position and modifiers.
     */
    // TODO: correlate this with TextImage.newTextGraphics
    fun newTextGraphics(topLeftCorner: TerminalPosition, size: TerminalSize): TextGraphics

    /**
     * Takes a [TextImage] and draws it on the surface this [TextGraphics] is targeting, given the
     * coordinates on the target that is specifying where the top-left corner of the image should be drawn.
     * This method will only draw a portion of the image to the target, as specified by the two last parameters.
     */
    fun drawImage(topLeft: TerminalPosition,
                  image: TextImage)

    /**
     * Puts a string on the screen at the specified position with the current colors and modifiers. If the string
     * contains newlines (\r and/or \n), the method will stop at the character before that; you have to manage
     * multi-line strings yourself! The current foreground color, background color and modifiers will be applied.
     */
    fun putString(position: TerminalPosition,
                  string: String,
                  extraModifiers: Set<Modifier> = setOf())

    /**
     * Draws a line from a specified position to a specified position, using a supplied character.
     * The current foreground color, background color and modifiers will be applied.
     */
    fun drawLine(fromPoint: TerminalPosition, toPoint: TerminalPosition, character: Char)

    /**
     * Draws the outline of a triangle on the screen, using a supplied character.
     * The triangle will begin at p1, go through p2 and then p3 and then back to p1.
     * The current foreground color, background color and modifiers will be applied.
     */
    fun drawTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char)

    /**
     * Draws a filled triangle, using a supplied character. The triangle will begin at p1, go
     * through p2 and then p3 and then back to p1. The current foreground color, background color
     * and modifiers will be applied.
     */
    fun fillTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char)

    /**
     * Draws the outline of a rectangle with a particular character (and the currently active colors and
     * modifiers). The topLeft coordinate is inclusive.
     *
     * For example, calling drawRectangle with size being the size of the terminal and top-left value
     * being the terminals top-left (0x0) corner will draw a border around the terminal.
     *
     * The current foreground color, background color and modifiers will be applied.
     */
    fun drawRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char)

    /**
     * Takes a rectangle and fills it with a particular character (and the currently active colors and
     * modifiers). The topLeft coordinate is inclusive.
     *
     * For example, calling fillRectangle with size being the size of the terminal and top-left
     * value being the terminals top-left (0x0) corner will fill the entire terminal with this character.
     *
     * The current foreground color, background color and modifiers will be applied.
     */
    fun fillRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char)

}
