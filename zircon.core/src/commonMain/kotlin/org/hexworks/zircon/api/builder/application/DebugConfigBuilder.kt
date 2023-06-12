package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for [DebugConfig]s.
 * @see DebugConfig for defaults
 */
@ZirconDsl
class DebugConfigBuilder : Builder<DebugConfig> {

    /**
     * If `true` a grid will be displayed around the tiles.
     */
    var displayGrid: Boolean = false

    /**
     * If `true` it will draw the positions of the individual tiles on the tiles
     */
    var displayCoordinates: Boolean = false

    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    var relaxBoundsCheck: Boolean = false

    override fun build() = DebugConfig(
        displayGrid = displayGrid,
        displayCoordinates = displayCoordinates,
        relaxBoundsCheck = relaxBoundsCheck
    )

    companion object {
        val DEFAULT_DEBUG_CONFIG = DebugConfigBuilder().build()
    }
}
