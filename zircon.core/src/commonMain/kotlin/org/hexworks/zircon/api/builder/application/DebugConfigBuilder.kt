package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import kotlin.jvm.JvmStatic

/**
 * Builder for [DebugConfig]s.
 *
 * All values default to `false`.
 */
data class DebugConfigBuilder(
        /**
         * If `true` a grid will be displayed around the tiles.
         */
        var displayGrid: Boolean = prototype.displayGrid,
        /**
         * If `true` it will draw the positions of the individual tiles on the tiles
         */
        var displayCoordinates: Boolean = prototype.displayCoordinates,
        /**
         * If `true` log messages will appear detailing the FPS characteristics
         */
        var displayFps: Boolean = prototype.displayFps,
        /**
         * if `true` no bounds check will be performed when adding/moving components
         */
        var relaxBoundsCheck: Boolean = prototype.relaxBoundsCheck
) : Builder<DebugConfig> {

    override fun build() = DebugConfig(
            displayGrid = displayGrid,
            displayCoordinates = displayCoordinates,
            displayFps = displayFps,
            relaxBoundsCheck = relaxBoundsCheck
    )

    override fun createCopy() = copy()

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

    companion object {

        private val prototype = DebugConfig()

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

    }
}
