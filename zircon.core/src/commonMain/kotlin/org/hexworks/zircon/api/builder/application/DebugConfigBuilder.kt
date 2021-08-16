package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import kotlin.jvm.JvmStatic

/**
 * Builder for [DebugConfig]s.
 * @see DebugConfig for defaults
 */
class DebugConfigBuilder private constructor(
    /**
     * If `true` a grid will be displayed around the tiles.
     */
    var displayGrid: Boolean = false,
    /**
     * If `true` it will draw the positions of the individual tiles on the tiles
     */
    var displayCoordinates: Boolean = false,
    /**
     * If `true` log messages will appear detailing the FPS characteristics
     */
    var displayFps: Boolean = false,
    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    var relaxBoundsCheck: Boolean = false
) : Builder<DebugConfig> {

    fun withRelaxBoundsCheck(relaxBoundsCheck: Boolean) = also {
        this.relaxBoundsCheck = relaxBoundsCheck
    }

    fun withDisplayGrid(displayGrid: Boolean) = also {
        this.displayGrid = displayGrid
    }

    fun withDisplayCoordinates(displayCoordinates: Boolean) = also {
        this.displayCoordinates = displayCoordinates
    }

    fun withDisplayFps(displayFps: Boolean) = also {
        this.displayFps = displayFps
    }

    override fun build() = DebugConfig(
        displayGrid = displayGrid,
        displayCoordinates = displayCoordinates,
        displayFps = displayFps,
        relaxBoundsCheck = relaxBoundsCheck
    )

    override fun createCopy() = DebugConfigBuilder(
        displayGrid = displayGrid,
        displayCoordinates = displayCoordinates,
        displayFps = displayFps,
        relaxBoundsCheck = relaxBoundsCheck
    )

    companion object {

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

    }
}
