package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder

/**
 * Contains the configuration to be used when debug mode is enabled.
 */
public class DebugConfig internal constructor(
    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    public val relaxBoundsCheck: Boolean,
    /**
     * When `true` the renderer draws the cell grid over the tiles.
     */
    public val displayGrid: Boolean = false,
) {

    public companion object {

        public val DEFAULT_DEBUG_CONFIG: DebugConfig = DebugConfigBuilder().build()
    }
}
