package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.tileset.impl.FontSettings

/**
 * Interface which adds functionality for overriding [Tileset]s used
 * in its implementors (components, layers, etc).
 */
interface FontOverride {

    /**
     * Tells whether there is currently an override [Tileset] present or not.
     */
    fun hasOverrideFont(): Boolean = getCurrentFont() !== FontSettings.NO_FONT

    /**
     * Returns the currently used [Tileset].
     */
    fun getCurrentFont(): Tileset

    /**
     * Sets the [Tileset] to use.
     * @return true if successful, false if not (if the tileset was set from another thread for example).
     */
    fun useFont(tileset: Tileset): Boolean

    /**
     * Sets the override [Tileset] to its default value (which is `NO_FONT`).
     */
    fun resetFont()
}
