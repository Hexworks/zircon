package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder

/**
 * Contains the configuration to be used when debug mode is enabled.
 */
class DebugConfig internal constructor(
    /**
     * If `true` a grid will be displayed around the tiles.
     */
    val displayGrid: Boolean,
    /**
     * If `true` it will draw the positions of the individual tiles on the tiles
     */
    val displayCoordinates: Boolean,
    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    val relaxBoundsCheck: Boolean
) {

    companion object {

        val DEFAULT_DEBUG_CONFIG = DebugConfigBuilder().build()
    }
}
