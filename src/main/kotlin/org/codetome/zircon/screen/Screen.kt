package org.codetome.zircon.screen

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Clearable
import org.codetome.zircon.behavior.CursorHolder
import org.codetome.zircon.behavior.DrawSurface
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.input.InputConsumer
import org.codetome.zircon.input.InputProvider
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
interface Screen
    : InputProvider, InputConsumer, Closeable, Clearable, Layerable, CursorHolder, DrawSurface {

    /**
     * Reads a character and its associated meta-data from the front-buffer and returns it encapsulated as a
     * [TextCharacter].
     */
    fun getFrontCharacter(position: Position): Optional<TextCharacter>

    /**
     * Reads a character and its associated meta-data from the back-buffer and returns it encapsulated as a
     * [TextCharacter].
     */
    fun getBackCharacter(position: Position): Optional<TextCharacter>

    /**
     * Same as [Screen.refresh] but forces a redraw of each character regardless of its changes.
     */
    fun display()

    /**
     * This method will take the content from the back-buffer and move it into the front-buffer,
     * making the changes visible to the terminal in the process.
     * <strong>Note that</strong> this method will compare the back-buffer to the frond-buffer and only
     * overwrite characters which are different in the two buffers. This will cause graphical artifacts
     * if you have multiple [Screen]s attached to the same [org.codetome.zircon.terminal.Terminal]!
     * In this case consider using [Screen.display] instead!
     */
    fun refresh()

}
