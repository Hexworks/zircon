package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.resource.BuiltInGraphicalTilesetResource

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
        private var defaultTileset: TilesetResource = CP437TilesetResources.wanderlust16x16(),
        private var defaultGraphicalTileset: TilesetResource = BuiltInGraphicalTilesetResource.NETHACK_16X16,
        private var defaultColorTheme: ColorTheme = ColorThemes.defaultTheme(),
        private var title: String = "Zircon Application",
        private var fullScreen: Boolean = false,
        private var debugMode: Boolean = false,
        private var defaultSize: Size = Size.defaultGridSize(),
        private var betaEnabled: Boolean = false,
        private var fpsLimit: Int = 60,
        private var debugConfig: DebugConfig = DebugConfigBuilder.newBuilder().build(),
        private var closeBehavior: CloseBehavior = CloseBehavior.EXIT_ON_CLOSE,
        private var shortcutsConfig: ShortcutsConfig = ShortcutsConfigBuilder.newBuilder().build())
    : Builder<AppConfig> {

    /**
     * Sets the [debugConfig] to use.
     */
    fun withDebugConfig(debugConfig: DebugConfig) = also {
        this.debugConfig = debugConfig
    }

    /**
     * Sets the [shortcutsConfig] to use.
     */
    fun withShortcutsConfig(shortcutsConfig: ShortcutsConfig) = also {
        this.shortcutsConfig = shortcutsConfig
    }

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

    /**
     * Sets the fps limit for continuous rendering.
     * **Note that** this is not supported for Swing Applications yet.
     */
    fun withFpsLimit(fpsLimit: Int) = also {
        require(fpsLimit > 0) {
            "Can't set an fps limit which is less than zero"
        }
        this.fpsLimit = fpsLimit
    }

    fun withSize(width: Int, height: Int) = withSize(Size.create(width, height))

    fun withDefaultTileset(defaultTileset: TilesetResource) = also {
        this.defaultTileset = defaultTileset
    }

    fun withDefaultGraphicalTileset(defaultGraphicalTileset: TilesetResource) = also {
        this.defaultGraphicalTileset = defaultGraphicalTileset
    }

    fun withCloseBehavior(closeBehavior: CloseBehavior) = also {
        this.closeBehavior = closeBehavior
    }

    fun enableBetaFeatures() = also {
        this.betaEnabled = true
    }

    fun disableBetaFeatures() = also {
        this.betaEnabled = false
    }

    override fun build() = AppConfig(
            blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
            cursorStyle = cursorStyle,
            cursorColor = cursorColor,
            isCursorBlinking = cursorBlinking,
            isClipboardAvailable = clipboardAvailable,
            defaultTileset = defaultTileset,
            defaultGraphicalTileset = defaultGraphicalTileset,
            defaultColorTheme = defaultColorTheme,
            debugMode = debugMode,
            size = defaultSize,
            fullScreen = fullScreen,
            betaEnabled = betaEnabled,
            title = title,
            fpsLimit = fpsLimit,
            debugConfig = debugConfig,
            closeBehavior = closeBehavior,
            shortcutsConfig = shortcutsConfig).also {
        RuntimeConfig.config = it
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = AppConfigBuilder()

    }
}
