package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.font.Font

/**
 * Interface which adds functionality for overriding [Font]s used
 * in its implementors (components, layers, etc).
 */
interface FontOverride<R> {

    /**
     * Returns the currently used [Font].
     */
    fun getCurrentFont(): Font<R>

    /**
     * Sets the [Font] to use.
     */
    fun useFont(font: Font<R>)
}