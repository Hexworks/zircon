package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.FontSettings

/**
 * Interface which adds functionality for overriding [Font]s used
 * in its implementors (components, layers, etc).
 */
interface FontOverride<R> {

    /**
     * Tells whether there is currently an override [Font] present or not.
     */
    fun hasOverrideFont(): Boolean = getCurrentFont() !== FontSettings.NO_FONT

    /**
     * Returns the currently used [Font].
     */
    fun getCurrentFont(): Font<R>

    /**
     * Sets the [Font] to use.
     */
    fun useFont(font: Font<R>)
}