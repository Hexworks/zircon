package org.codetome.zircon.api.builder.grid

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.ApplicationConfiguration
import org.codetome.zircon.api.grid.CursorStyle
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.config.RuntimeConfig

/**
 * Builder for [ApplicationConfiguration]s.
 * Defaults are:
 * - default `title` is "Zircon Application"
 */
data class ApplicationConfigurationBuilder(
        private var blinkLengthInMilliSeconds: Long = 500,
        private var cursorStyle: CursorStyle = CursorStyle.USE_CHARACTER_FOREGROUND,
        private var cursorColor: TextColor = TextColor.defaultForegroundColor(),
        private var cursorBlinking: Boolean = false,
        private var clipboardAvailable: Boolean = true,
        private var defaultTileset: TilesetResource<out Tile> = CP437TilesetResource.WANDERLUST_16X16,
        private var title: String = "Zircon Application",
        private var fullScreen: Boolean = false,
        private var debugMode: Boolean = false,
        private var defaultSize: Size = Size.defaultTerminalSize())
    : Builder<ApplicationConfiguration> {

    override fun build() = ApplicationConfiguration(
            blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
            cursorStyle = cursorStyle,
            cursorColor = cursorColor,
            isCursorBlinking = cursorBlinking,
            isClipboardAvailable = clipboardAvailable,
            defaultTileset = defaultTileset,
            debugMode = debugMode,
            size = defaultSize,
            fullScreen = fullScreen).also {
        RuntimeConfig.config = it
    }

    override fun createCopy() = copy()

    /**
     * Sets the length of a blink. All blinking characters will use this setting.
     */
    fun blinkLengthInMilliSeconds(blinkLengthInMilliSeconds: Long) = also {
        this.blinkLengthInMilliSeconds = blinkLengthInMilliSeconds
    }

    /**
     * Sets the title to use on created [TileGrid]s created by this shape.
     * Default is "Zircon TileGrid"
     */
    fun title(title: String) = also {
        this.title = title
    }

    fun fullScreen() = also {
        fullScreen = true
    }

    /**
     * Sets the cursor style. See: [CursorStyle].
     */
    fun cursorStyle(cursorStyle: CursorStyle) = also {
        this.cursorStyle = cursorStyle
    }

    /**
     * Sets the color of the cursor.
     */
    fun cursorColor(cursorColor: TextColor) = also {
        this.cursorColor = cursorColor
    }

    /**
     * Sets whether the cursor blinks or not.
     */
    fun cursorBlinking(cursorBlinking: Boolean) = also {
        this.cursorBlinking = cursorBlinking
    }

    /**
     * Enables or disables clipboard. <code>Shift + Insert</code> will paste text
     * at the cursor location if clipboard is available.
     */
    fun clipboardAvailable(clipboardAvailable: Boolean) = also {
        this.clipboardAvailable = clipboardAvailable
    }

    fun debugMode(debugMode: Boolean) = also {
        this.debugMode = debugMode
    }

    fun defaultSize(size: Size) = also {
        this.defaultSize = size
    }

    fun defaultTileset(defaultTileset: TilesetResource<out Tile>) = also {
        this.defaultTileset = defaultTileset
    }

    companion object {

        fun newBuilder() = ApplicationConfigurationBuilder()

    }
}
