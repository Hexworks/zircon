package org.codetome.zircon.screen

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.terminal.TerminalSize
import java.io.Closeable
import java.util.*

/**
 * [Screen] is a fundamental layer presenting the terminal as a bitmap-like surface where you can perform
 * smaller in-memory operations to a back-buffer, effectively painting out the terminal as you'd like it,
 * and then call `refresh` to have the screen automatically apply the changes in the back-buffer to the real
 * terminal. The screen tracks what's visible through a front-buffer, but this is completely managed
 * internally and cannot be expected to know what the terminal looks like if it's being modified externally.
 * <strong>Note that</strong> more than one [Screen]s can be attached to the same backing
 * [org.codetome.zircon.terminal.Terminal]. If you want a [Screen] to be displayed use the
 * [Screen.display] method.
 */
interface Screen : InputProvider, Closeable {

    /**
     * Erases all the characters on the screen, effectively giving you a blank area.
     * The default background color will be used. This is effectively the same as calling
     * <pre>fill(TerminalPosition.TOP_LEFT_CORNER, getSize(), TextColor.ANSI.Default)</pre>.
     *
     * Please note that calling this method will only affect the back buffer,
     * you need to call refresh to make the change visible.
     */
    fun clear()

    fun getCursorPosition(): TerminalPosition

    fun setCursorPosition(position: TerminalPosition)

    fun isCursorVisible(): Boolean

    fun setCursorVisible(cursorVisible: Boolean)

    fun getTabBehavior(): TabBehavior

    fun setTabBehavior(tabBehavior: TabBehavior)

    fun getTerminalSize(): TerminalSize

    /**
     * Reads a character and its associated meta-data from the front-buffer and returns it encapsulated as a
     * [TextCharacter].
     */
    fun getFrontCharacter(position: TerminalPosition): TextCharacter

    /**
     * Reads a character and its associated meta-data from the back-buffer and returns it encapsulated as a
     * [TextCharacter].
     */
    fun getBackCharacter(position: TerminalPosition): TextCharacter

    /**
     * Sets a character in the back-buffer to a specified value with specified colors and modifiers.
     */
    fun setCharacter(position: TerminalPosition, screenCharacter: TextCharacter)

    /**
     * Creates a new [TextGraphics] objects that is targeting this [Screen] for writing to.
     * Any operations done on this [TextGraphics] will be affecting this screen.
     * Remember to call `refresh()` on the screen to see your changes.
     */
    fun newTextGraphics(): TextGraphics

    /**
     * Same as [Screen.refresh] but forces a redraw of each character regardless of its changes.
     */
    fun display()

    /**
     * This method will take the content from the back-buffer and move it into the front-buffer, making the changes
     * visible to the terminal in the process.
     * <strong>Note that</strong> this method will compare the back-buffer to the frond-buffer and only
     * overwrite characters which are different in the two buffers. This will cause graphical artifacts if you
     * have multiple [Screen]s attached to the same [org.codetome.zircon.terminal.Terminal]!
     * In this case consider using [Screen.display] instead!
     */
    fun refresh()

}
