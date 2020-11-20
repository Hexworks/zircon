package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import kotlin.jvm.JvmStatic

/**
 * Builder for [DebugConfig]s.
 * @see DebugConfig for defaults
 */
data class DebugConfigBuilder(
        var debugConfig: DebugConfig = DebugConfig.defaultConfig()
) : Builder<DebugConfig> {

    fun withRelaxBoundsCheck(relaxBoundsCheck: Boolean) = also {
        debugConfig = debugConfig.copy(relaxBoundsCheck = relaxBoundsCheck)
    }

    fun withDisplayGrid(displayGrid: Boolean) = also {
        debugConfig.copy(displayGrid = displayGrid)
    }

    fun withDisplayCoordinates(displayCoordinates: Boolean) = also {
        debugConfig.copy(displayCoordinates = displayCoordinates)
    }

    fun withDisplayFps(displayFps: Boolean) = also {
        debugConfig.copy(displayFps = displayFps)
    }

    override fun build() = debugConfig

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

    }
}
