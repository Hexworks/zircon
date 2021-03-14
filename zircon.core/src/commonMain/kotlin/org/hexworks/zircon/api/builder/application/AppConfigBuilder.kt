package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * This *builder* class can be used to build [AppConfig] instances. This builder
 * has sensible default values so
 * @see AppConfig for all the default values.
 */
data class AppConfigBuilder(
    private var config: AppConfig = AppConfig.defaultConfiguration()
) : Builder<AppConfig> {

    /**
     * Sets the [debugConfig] to be used when [debugMode] is `true`.
     * @see DebugConfigBuilder
     * @see DebugConfig
     * @see AppConfig.debugMode
     * @see AppConfig.debugConfig
     */
    fun withDebugConfig(debugConfig: DebugConfig) = also {
        config = config.copy(debugConfig = debugConfig)
    }

    /**
     * Toggles debug mode on or off.
     * @see DebugConfigBuilder
     * @see DebugConfig
     * @see AppConfig.debugMode
     * @see AppConfig.debugConfig
     */
    fun withDebugMode(debugMode: Boolean) = also {
        config = config.copy(debugMode = debugMode)
    }

    /**
     * Sets the [shortcutsConfig] to use.
     * @see AppConfig.shortcutsConfig
     * @see ShortcutsConfig
     * @see ShortcutsConfigBuilder
     */
    fun withShortcutsConfig(shortcutsConfig: ShortcutsConfig) = also {
        config = config.copy(shortcutsConfig = shortcutsConfig)
    }

    /**
     * Sets the length of a blink. All blinking characters will use this setting.
     * @see AppConfig.blinkLengthInMilliSeconds
     */
    fun withBlinkLengthInMilliSeconds(blinkLengthInMilliSeconds: Long) = also {
        config = config.copy(blinkLengthInMilliSeconds = blinkLengthInMilliSeconds)
    }

    /**
     * Sets the application window's title.
     * @see AppConfig.title
     */
    fun withTitle(title: String) = also {
        config = config.copy(title = title)
    }

    /**
     * Toggles whether the resulting application is full screen or not.
     * @see AppConfig.fullScreen
     */
    @JvmOverloads
    fun withFullScreen(fullScreen: Boolean = true) = also {
        config = config.copy(
            fullScreen = fullScreen,
            borderless = fullScreen
        )
    }

    /**
     * Toggles whether the resulting application will be borderless or not.
     * @see AppConfig.borderless
     */
    @JvmOverloads
    fun withBorderless(borderless: Boolean = true) = also {
        config = config.copy(borderless = borderless)
    }

    /**
     * Sets the [CursorStyle] to be used when there is a cursor displayed.
     * @see AppConfig.cursorStyle
     */
    fun withCursorStyle(cursorStyle: CursorStyle) = also {
        config = config.copy(cursorStyle = cursorStyle)
    }

    /**
     * Sets the color of the cursor.
     * @see AppConfig.cursorColor
     */
    fun withCursorColor(cursorColor: TileColor) = also {
        config = config.copy(cursorColor = cursorColor)
    }

    /**
     * Sets whether the cursor blinks or not.
     * @see AppConfig.isCursorBlinking
     */
    fun withCursorBlinking(isCursorBlinking: Boolean) = also {
        config = config.copy(isCursorBlinking = isCursorBlinking)
    }

    /**
     * Enables or disables clipboard. <code>Shift + Insert</code> will paste text
     * at the cursor location if clipboard is available.
     * @see AppConfig.isClipboardAvailable
     */
    fun withClipboardAvailable(isClipboardAvailable: Boolean) = also {
        config = config.copy(isClipboardAvailable = isClipboardAvailable)
    }

    /**
     * Sets the size the [Application.tileGrid] will have.
     * @see AppConfig.size
     */
    fun withSize(width: Int, height: Int) = withSize(Size.create(width, height))

    /**
     * Sets the size the [Application.tileGrid] will have.
     * @see AppConfig.size
     */
    fun withSize(size: Size) = also {
        config = config.copy(size = size)
    }

    /**
     * Sets the fps limit for continuous rendering.
     * **Note that** this is not supported for Swing Applications yet.
     */
    fun withFpsLimit(fpsLimit: Int) = also {
        require(fpsLimit > 0) {
            "Can't set an fps limit which is less than zero"
        }
        config = config.copy(fpsLimit = fpsLimit)
    }

    /**
     * Sets the default tileset to be used.
     * @see AppConfig.defaultTileset
     */
    fun withDefaultTileset(defaultTileset: TilesetResource) = also {
        config = config.copy(defaultTileset = defaultTileset)
    }

    /**
     * Sets the default graphical tileset to be used.
     * @see AppConfig.defaultGraphicalTileset
     */
    fun withDefaultGraphicalTileset(defaultGraphicalTileset: TilesetResource) = also {
        config = config.copy(defaultGraphicalTileset = defaultGraphicalTileset)
    }

    /**
     * Sets the close behavior to be used.
     * @see AppConfig.closeBehavior
     */
    fun withCloseBehavior(closeBehavior: CloseBehavior) = also {
        config = config.copy(closeBehavior = closeBehavior)
    }

    /**
     * Sets the image that should be used as an application icon as a [ByteArray].
     * If set [iconPath] will be set to `null`.
     */
    fun withIcon(iconData: ByteArray) = also {
        config = config.copy(
            iconData = iconData,
            iconPath = null
        )
    }

    /**
     * Sets the image's *resource path* that should be used as an application icon.
     * If set, [iconData] will be set to `null`.
     */
    fun withIcon(iconPath: String) = also {
        config = config.copy(
            iconPath = iconPath,
            iconData = null
        )
    }

    /**
     * Adds a custom property into the AppConfig object. This can later be retrieved using [AppConfig.getProperty].
     *
     * ### End Developers
     *
     * You probably don't need to call this API.
     *
     * ### Plugin Developers
     *
     * Write extension methods off of [AppConfigBuilder] that call this API in order to enable end developers
     * to pass configuration in through AppConfig that your plugin can later use. It's recommended that [key]
     * be an `object` with minimal visibility (e.g. `internal`).
     *
     * @sample org.hexworks.zircon.api.application.AppConfigTest.propertyExample
     */
    fun <T : Any> withProperty(key: AppConfigKey<T>, value: T): AppConfigBuilder = also {
        config = config.copy(
            customProperties = config.customProperties + (key to value)
        )
    }

    /**
     * Sets the additional tileset loaders that should be attempted before falling back to the default
     * tileset loader the renderer uses.
     *
     * **Order matters.** Loaders earlier in the list will be attempted first.
     */
    fun withTilesetLoaders(vararg loaders: TilesetLoader<*>) = also {
        config = config.copy(
            tilesetLoaders = loaders.toList()
        )
    }

    @Deprecated("This will be removed in the next version, as the behavior is inconsistent.")
    fun withFullScreen(screenWidth: Int, screenHeight: Int) = also {
        throw UnsupportedOperationException("Unstable api, use withFullScreen(true) instead")
    }

    @Deprecated(
        message = "Use withBorderless instead",
        replaceWith = ReplaceWith("this.withBorderless(true)")
    )
    fun borderless() = withBorderless(true)

    @Deprecated("This feature will be removed in the next release, look at the debug features instead.")
    fun enableBetaFeatures() = this

    @Deprecated("This feature will be removed in the next release, look at the debug features instead.")
    fun disableBetaFeatures() = this

    @Deprecated(message = "use withFullScreen instead", ReplaceWith("this.withFullScreen(true)"))
    fun fullScreen() = withFullScreen(true)

    @Deprecated(
        message = "use withFullScreen instead",
        replaceWith = ReplaceWith("this.withFullScreen(screenWidth, screenHeight)")
    )
    fun fullScreen(screenWidth: Int, screenHeight: Int) = withFullScreen(screenWidth, screenHeight)


    override fun build(): AppConfig {
        return config.also {
            RuntimeConfig.config = it
        }
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = AppConfigBuilder()

    }
}
