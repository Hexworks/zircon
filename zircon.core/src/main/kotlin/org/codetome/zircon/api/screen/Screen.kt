package org.codetome.zircon.api.screen

import org.codetome.zircon.api.component.ContainerHandler
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.behavior.Identifiable

/**
 * [Screen] is a fundamental layer presenting the grid as a bitmap-like surface where you can perform
 * smaller in-memory operations to a back-buffer, effectively painting out the grid as you'd like it,
 * and then call `refresh` to have the screen automatically apply the changes in the back-buffer to the real
 * grid. The screen tracks what has changed in the back-buffer, but this is completely managed
 * internally and cannot be expected to know what the grid looks like if it's being modified externally.
 * <strong>Note that</strong> more than one [Screen]s can be attached to the same backing
 * [org.codetome.zircon.api.grid.TileGrid]. If you want a [Screen] to be displayed without
 * tracking the changes use the [Screen.display] function.
 */
interface Screen
    : TileGrid, ContainerHandler, Identifiable {

    /**
     * This function will take the content from the back-buffer and move it into the front-buffer
     * (a TileGrid for example), making the changes visible in the process.
     * <strong>Note that</strong> this function will use the tracked changes since the last refresh/display
     * operation and only overwrite characters which were changed. This will cause graphical artifacts
     * if you have multiple [Screen]s attached to the same [org.codetome.zircon.api.grid.TileGrid]!
     * In this case consider using [Screen.display] instead!
     */
    fun refresh()

    /**
     * Same as [Screen.refresh] but forces a redraw of each character regardless of its changes.
     */
    fun display()

}
