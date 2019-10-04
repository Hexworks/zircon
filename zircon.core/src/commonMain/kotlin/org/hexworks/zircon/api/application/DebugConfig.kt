package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder
import kotlin.jvm.JvmStatic

data class DebugConfig(
        val displayGrid: Boolean,
        val displayCoordinates: Boolean,
        val displayFps: Boolean) {

    companion object {

        @JvmStatic
        fun defaultConfig() = DebugConfigBuilder.newBuilder().build()
    }
}
