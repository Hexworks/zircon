package org.codetome.zircon.api.screen

import org.codetome.zircon.api.behavior.Clearable
import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.behavior.TilesetOverride

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
interface Screen<T: Any, S: Any>
    : Closeable, Clearable, DrawSurface<T>, Layerable, TilesetOverride<T, S> {

    /**
     * Moves the contents of this [Screen] to the underlying [org.codetome.zircon.api.grid.TileGrid].
     */
    fun display()

}
