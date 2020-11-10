package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder
import kotlin.jvm.JvmStatic

/**
 * Contains the configuration to be used when debug mode is enabled.
 */
data class DebugConfig(
        /**
         * If `true` a grid will be displayed around the tiles.
         */
        val displayGrid: Boolean = false,
        /**
         * If `true` it will draw the positions of the individual tiles on the tiles
         */
        val displayCoordinates: Boolean = false,
        /**
         * If `true` log messages will appear detailing the FPS characteristics
         */
        val displayFps: Boolean = false,
        // TODO: mention new feature in release
        /**
         * if `true` no bounds check will be performed when adding/moving components
         */
        val relaxBoundsCheck: Boolean = false
) {

    companion object {

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

        @JvmStatic
        fun defaultConfig() = DebugConfigBuilder.newBuilder().build()
    }
}
