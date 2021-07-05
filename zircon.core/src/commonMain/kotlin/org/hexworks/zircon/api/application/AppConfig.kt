package org.hexworks.zircon.api.application

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.renderer.Renderer
import kotlin.jvm.JvmStatic

/**
 * Object that encapsulates the configuration parameters for an [Application].
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 *
 * Typically you'll want to construct this using [AppConfigBuilder], not AppConfig's constructor.
 */
@Suppress("ArrayInDataClass")
data class AppConfig(
    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    val blinkLengthInMilliSeconds: Long = 500,
    /**
     * The [CursorStyle] to be used when the cursor is displayed.
     * Default is [CursorStyle.FIXED_BACKGROUND].
     */
    val cursorStyle: CursorStyle = CursorStyle.FIXED_BACKGROUND,
    /**
     * The [TileColor] to be used when drawing a cursor.
     * Default is [TileColor.defaultForegroundColor()] (black).
     */
    val cursorColor: TileColor = TileColor.defaultForegroundColor(),
    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    val isCursorBlinking: Boolean = false,
    /**
     * Can be used to control the availability of the clipboard.
     * Default is `true`
     */
    val isClipboardAvailable: Boolean = true,
    /**
     * The tileset to be used when no tileset is specified for the [Application].
     * Default is [CP437TilesetResources.wanderlust16x16]
     */
    val defaultTileset: TilesetResource = CP437TilesetResources.wanderlust16x16(),
    /**
     * The default graphical tileset to be used when no graphical tileset is specified.
     * Default is [GraphicalTilesetResources.nethack16x16]
     */
    val defaultGraphicalTileset: TilesetResource = GraphicalTilesetResources.nethack16x16(),
    /**
     * The default [ColorTheme] to be used when no color theme is specified.
     * Default is [ColorThemes.defaultTheme]
     */
    val defaultColorTheme: ColorTheme = ColorThemes.defaultTheme(),
    /**
     * Can be used to switch debug mode on or off.
     * Default is `false`.
     */
    val debugMode: Boolean = false,
    /**
     * Controls the [Size] of the resulting tile grid when the [Application]
     * is started.
     * Default is `80x40`
     */
    val size: Size = Size.create(80, 40),
    /**
     * Controls if the [Application] will be full screen or not.
     * Default is `false`
     */
    val fullScreen: Boolean = false,
    /**
     * Controls if the [Application] will be borderless or not.
     * Default is `false`
     */
    val borderless: Boolean = false,
    /**
     * Sets the title of the application window.
     * Default is `"Zircon Application"`
     */
    val title: String = "Zircon Application",
    /**
     * Sets the fps limit of the resulting [Application].
     * Default is `60`
     */
    val fpsLimit: Int = 60,
    /**
     * Sets the [DebugConfig] to be used when [debugMode] is `true`.
     * By default all settings are `false`.
     */
    val debugConfig: DebugConfig = DebugConfig.defaultConfig(),
    /**
     * Determines the [CloseBehavior] when the application windows is closed.
     * Default is [CloseBehavior.EXIT_ON_CLOSE]
     * @see CloseBehavior
     */
    val closeBehavior: CloseBehavior = CloseBehavior.EXIT_ON_CLOSE,
    /**
     * Determines the [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    val shortcutsConfig: ShortcutsConfig = ShortcutsConfig(),
    /**
     * If set [iconData] contains the bytes of the icon image that will
     * be used in the application window.
     */
    val iconData: ByteArray? = null,
    /**
     * If set [iconPath] will contain the path of the resource that points
     * to an icon image that will be used in the application window.
     */
    val iconPath: String? = null,
    /**
     * If set, contains custom properties that plugin authors can set and access.
     */
    internal val customProperties: Map<AppConfigKey<*>, Any> = emptyMap(),
    /**
     * If set [tilesetLoaders] will contain the list of [TilesetLoaders][TilesetLoader] to try to use
     * before using the default [TilesetLoader] of the [Renderer].
     */
    val tilesetLoaders: List<TilesetLoader<*>> = emptyList()
) {

    /**
     * Tells whether bounds check should be performed or not.
     * This depends on the various debug mode configurations.
     */
    fun shouldCheckBounds() = !debugMode || !debugConfig.relaxBoundsCheck

    /**
     * Retrieve a custom property set earlier using [AppConfigBuilder.withProperty]. If this property was
     * never set, returns an empty [Maybe].
     *
     * **Note that** you probably don't need to call this API.
     *
     * TODO: this should have been the getOrNull variant, but now we can't modify it otherwise it would break the API
     */
    operator fun <T : Any> get(key: AppConfigKey<T>): Maybe<T> {
        val value: Any? = customProperties[key]
        // This is actually a safe cast because of the way `withProperty` is defined.
        @Suppress("UNCHECKED_CAST")
        return Maybe.ofNullable(value as T?)
    }

    /**
     * Retrieve a custom property set earlier using [AppConfigBuilder.withProperty]. If this property was
     * never set, returns an empty [Maybe].
     *
     * **Note that** you probably don't need to call this API.
     */
    fun <T : Any> getOrNull(key: AppConfigKey<T>): T? {
        val value: Any? = customProperties[key]
        // This is actually a safe cast because of the way `withProperty` is defined.
        @Suppress("UNCHECKED_CAST")
        return value as T?
    }

    companion object {

        @JvmStatic
        fun newBuilder() = AppConfigBuilder()

        @JvmStatic
        fun defaultConfiguration() = AppConfig()

    }
}
