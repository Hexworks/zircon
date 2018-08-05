package org.codetome.zircon.api.grid

import org.codetome.zircon.api.builder.grid.AppConfigBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource

/**
 * Object that encapsulates the configuration parameters for an application.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
data class AppConfig(
        val blinkLengthInMilliSeconds: Long,
        val cursorStyle: CursorStyle,
        val cursorColor: TextColor,
        val isCursorBlinking: Boolean,
        val isClipboardAvailable: Boolean,
        val defaultTileset: TilesetResource<out Tile>,
        val debugMode: Boolean,
        val size: Size,
        val fullScreen: Boolean) {

    companion object {

        fun defaultConfiguration() = AppConfigBuilder.newBuilder().build()

    }
}
