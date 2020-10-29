package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import kotlin.jvm.JvmStatic

/**
 * Builder for [DebugConfig]s.
 * All values default to `false`.
 */
data class DebugConfigBuilder(
        var displayGrid: Boolean = false,
        var displayCoordinates: Boolean = false,
        var displayFps: Boolean = false,
        var relaxBoundsCheck: Boolean = false
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

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

    }
}
