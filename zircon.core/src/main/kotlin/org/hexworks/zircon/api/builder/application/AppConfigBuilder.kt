package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
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
        private var defaultColorTheme: ColorTheme = ColorThemeResource.TECH_LIGHT.getTheme(),
        private var title: String = "Zircon Application",
        private var fullScreen: Boolean = false,
        private var debugMode: Boolean = false,
        private var defaultSize: Size = Size.defaultTerminalSize(),
        private var betaEnabled: Boolean = false)
    : Builder<AppConfig> {

    override fun build() = AppConfig(
            blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
            cursorStyle = cursorStyle,
            cursorColor = cursorColor,
            isCursorBlinking = cursorBlinking,
            isClipboardAvailable = clipboardAvailable,
            defaultTileset = defaultTileset,
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
    fun cursorColor(cursorColor: TileColor) = also {
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

    fun defaultTileset(defaultTileset: TilesetResource) = also {
        this.defaultTileset = defaultTileset
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
