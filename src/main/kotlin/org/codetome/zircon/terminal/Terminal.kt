@file:Suppress("unused")

package org.codetome.zircon.terminal

import org.codetome.zircon.Size
import org.codetome.zircon.behavior.*
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.input.InputConsumer
import org.codetome.zircon.input.InputProvider
import java.io.Closeable

/**
 * This is the main terminal interface, at the lowest level supported. You can write your own
 * implementation of this if you want to target an exotic graphical environment (like SWT),
 * but you should probably extend [AbstractTerminal] instead of implementing this interface directly.
 *
 * The normal way you interact in Java with a terminal is through the standard output (System.out)
 * and standard error (System.err) and it's usually through printing text only.
 * This interface abstracts a terminal at a more fundamental level, expressing methods for not only printing
 * text but also changing colors, moving the cursor to new positions, enable special modifiers and get
 * notified when the terminal's size has changed.
 *
 * If you want to write an application that has a very precise control of the terminal, this is the
 * interface you should be programming against.
 */
interface Terminal
    : InputProvider, InputConsumer, Closeable, Clearable,
        StyleSet, CursorHolder, Layerable, DrawSurface, ContainerHolder {

    /**
     * Prints one character to the terminal at the current cursor location.
     * Please note that the cursor will then move one column to the right, so multiple calls to
     * [Terminal.putCharacter] will print out a text string without the need
     * to reposition the text cursor. If you reach the end of the line while putting characters
     * using this method, you can expect the text cursor to move to the beginning of the next line.
     *
     * If you try to print non-printable control characters, the terminal will ignore them.
     */
    fun putCharacter(c: Char)

    /**
     * Adds a [TerminalResizeListener] to be called when the terminal has changed size.
     */
    fun addResizeListener(listener: TerminalResizeListener)

    /**
     * Removes a [TerminalResizeListener] from the list of listeners to be notified when
     * the terminal has changed size.
     */
    fun removeResizeListener(listener: TerminalResizeListener)

    /**
     * Changes the visible size of the terminal. If you call this method with a size
     * that is different from the current size of the terminal, the resize event
     * will be fired on all listeners.
     */
    fun setSize(newSize: Size)

    /**
     * Calls [Terminal.flush] on the underlying [java.io.OutputStream] object, or whatever
     * other implementation this terminal is built around. Some implementing classes of this
     * interface (like [org.codetome.zircon.terminal.swing.SwingTerminalCanvas])
     * doesn't do anything as it doesn't really apply to them.
     */
    fun flush()

}

