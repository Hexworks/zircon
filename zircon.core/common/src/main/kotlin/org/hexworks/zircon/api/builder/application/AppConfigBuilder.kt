package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

/**
 * Builder for [AppConfig]s.
 * Defaults are:
 * - default `title` is "Zircon Application"
 */
data class AppConfigBuilder(
        private var blinkLengthInMilliSeconds: Long = 500,
        private var cursorStyle: CursorStyle = CursorStyle.FIXED_BACKGROUND,
        private var cursorColor: TileColor = TileColor.defaultForegroundColor(),
        private var cursorBlinking: Boolean = false,
        private var clipboardAvailable: Boolean = true,
        private var defaultTileset: TilesetResource = BuiltInCP437TilesetResource.ROGUE_YUN_16X16,
        private var defaultGraphicTileset: TilesetResource = BuiltInGraphicTilesetResource.NETHACK_16X16,
        private var defaultColorTheme: ColorTheme = ColorThemeResource.TECH_LIGHT.getTheme(),
        private var title: String = "Zircon Application",
        private var fullScreen: Boolean = false,
        private var debugMode: Boolean = false,
        private var defaultSize: Size = Size.defaultGridSize(),
        private var betaEnabled: Boolean = false)
    : Builder<AppConfig> {

    override fun build() = AppConfig(
            blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
            cursorStyle = cursorStyle,
            cursorColor = cursorColor,
            isCursorBlinking = cursorBlinking,
            isClipboardAvailable = clipboardAvailable,
            defaultTileset = defaultTileset,
            defaultGraphicTileset = defaultGraphicTileset,
            defaultColorTheme = defaultColorTheme,
            debugMode = debugMode,
            size = defaultSize,
            fullScreen = fullScreen,
            betaEnabled = betaEnabled).also {
        RuntimeConfig.config = it
    }

    override fun createCopy() = copy()

    /**
     * Sets the length of a blink. All blinking characters will use this setting.
     */
    fun withBlinkLengthInMilliSeconds(blinkLengthInMilliSeconds: Long) = also {
        this.blinkLengthInMilliSeconds = blinkLengthInMilliSeconds
    }

    /**
     * Sets the title to use on created [TileGrid]s created by this shape.
     * Default is "Zircon TileGrid"
     */
    fun withTitle(title: String) = also {
        this.title = title
    }

    fun fullScreen() = also {
        fullScreen = true
    }

    /**
     * Sets the cursor style. See: [CursorStyle].
     */
    fun withCursorStyle(cursorStyle: CursorStyle) = also {
        this.cursorStyle = cursorStyle
    }

    /**
     * Sets the color of the cursor.
     */
    fun withCursorColor(cursorColor: TileColor) = also {
        this.cursorColor = cursorColor
    }

    /**
     * Sets whether the cursor blinks or not.
     */
    fun withCursorBlinking(cursorBlinking: Boolean) = also {
        this.cursorBlinking = cursorBlinking
    }

    /**
     * Enables or disables clipboard. <code>Shift + Insert</code> will paste text
     * at the cursor location if clipboard is available.
     */
    fun withClipboardAvailable(clipboardAvailable: Boolean) = also {
        this.clipboardAvailable = clipboardAvailable
    }

    fun withDebugMode(debugMode: Boolean) = also {
        this.debugMode = debugMode
    }

    fun withSize(size: Size) = also {
        this.defaultSize = size
    }

    fun withDefaultTileset(defaultTileset: TilesetResource) = also {
        this.defaultTileset = defaultTileset
    }

    fun withDefaultGraphicTileset(defaultGraphicTileset: TilesetResource) = also {
        this.defaultGraphicTileset = defaultGraphicTileset
    }

    fun enableBetaFeatures() = also {
        this.betaEnabled = true
    }

    fun disableBetaFeatures() = also {
        this.betaEnabled = false
    }

    companion object {

        fun newBuilder() = AppConfigBuilder()

    }
}
