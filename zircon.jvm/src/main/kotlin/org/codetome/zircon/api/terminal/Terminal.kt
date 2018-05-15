@file:Suppress("unused")

package org.codetome.zircon.api.terminal

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.*
import java.io.Closeable

/**
 * This is the main terminal interface, at the lowest level supported. You can write your own
 * implementation of this if you want to target an exotic graphical environment (like SWT),
 * but you should probably wrap [org.codetome.zircon.internal.terminal.virtual.VirtualTerminal]
 * and delegate to it instead of implementing this interface directly.
 *
 * The normal way you interact in Java with a terminal is through the standard output (System.out)
 * and standard error (System.err) and it's usually through printing characters only.
 * This interface abstracts a terminal at a more fundamental level, expressing methods for not only printing
 * title but also changing colors, moving the cursor to new positions, enable special modifiers and get
 * notified when the terminal's size has changed.
 *
 * If you want to write an application that has a very precise control of the terminal, this is the
 * interface you should be programming against.
 */
interface Terminal
    : Closeable, Clearable, Styleable, TypingSupport, Layerable, DrawSurface, InputEmitter, FontOverride, ShutdownHook {

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
     * interface (like [org.codetome.zircon.internal.terminal.virtual.VirtualTerminal])
     * doesn't do anything as it doesn't really apply to them.
     */
    fun flush()

}

