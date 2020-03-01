package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.JvmStatic

/**
 * Object that encapsulates the configuration parameters for an application.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
data class AppConfig(
        val blinkLengthInMilliSeconds: Long,
        val cursorStyle: CursorStyle,
        val cursorColor: TileColor,
        val isCursorBlinking: Boolean,
        val isClipboardAvailable: Boolean,
        val defaultTileset: TilesetResource,
        val defaultGraphicalTileset: TilesetResource,
        val defaultColorTheme: ColorTheme,
        val debugMode: Boolean,
        val size: Size,
        val fullScreen: Boolean,
        val betaEnabled: Boolean,
        val title: String,
        val fpsLimit: Int,
        val debugConfig: DebugConfig,
        val closeBehavior: CloseBehavior
) {

    companion object {

        @JvmStatic
        fun newBuilder() = AppConfigBuilder()

        @JvmStatic
        fun defaultConfiguration() = AppConfigBuilder.newBuilder().build()

    }
}
