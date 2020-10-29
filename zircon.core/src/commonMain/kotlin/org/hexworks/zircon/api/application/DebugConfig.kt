package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder
import kotlin.jvm.JvmStatic

data class DebugConfig(
        val displayGrid: Boolean,
        val displayCoordinates: Boolean,
        val displayFps: Boolean,
        // TODO: mention new feature in release
        val relaxBoundsCheck: Boolean
) {

    companion object {

        @JvmStatic
        fun newBuilder() = DebugConfigBuilder()

        @JvmStatic
        fun defaultConfig() = DebugConfigBuilder.newBuilder().build()
    }
}
