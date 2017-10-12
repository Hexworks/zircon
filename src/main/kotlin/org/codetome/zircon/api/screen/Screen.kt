package org.codetome.zircon.api.screen

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.component.ContainerHandler
import org.codetome.zircon.internal.behavior.Identifiable
import java.awt.image.BufferedImage
import java.io.Closeable

/**
 * [Screen] is a fundamental layer presenting the terminal as a bitmap-like surface where you can perform
 * smaller in-memory operations to a back-buffer, effectively painting out the terminal as you'd like it,
 * and then call `refresh` to have the screen automatically apply the changes in the back-buffer to the real
 * terminal. The screen tracks what has changed in the back-buffer, but this is completely managed
 * internally and cannot be expected to know what the terminal looks like if it's being modified externally.
 * <strong>Note that</strong> more than one [Screen]s can be attached to the same backing
 * [org.codetome.zircon.api.terminal.Terminal]. If you want a [Screen] to be displayed without
 * tracking the changes use the [Screen.display] function.
 */
interface Screen
    : Closeable, Clearable, Layerable, CursorHandler, ContainerHandler, DrawSurface, InputEmitter, Identifiable, FontOverride {

    /**
     * This function will take the content from the back-buffer and move it into the front-buffer
     * (a Terminal for example), making the changes visible in the process.
     * <strong>Note that</strong> this function will use the tracked changes since the last refresh/display
     * operation and only overwrite characters which were changed. This will cause graphical artifacts
     * if you have multiple [Screen]s attached to the same [org.codetome.zircon.api.terminal.Terminal]!
     * In this case consider using [Screen.display] instead!
     */
    fun refresh()

    /**
     * Same as [Screen.refresh] but forces a redraw of each character regardless of its changes.
     */
    fun display()

}
