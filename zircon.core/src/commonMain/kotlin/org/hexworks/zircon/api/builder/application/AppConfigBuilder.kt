package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.modifier.TextureTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass


/**
 * This *builder* class can be used to build [AppConfig] instances. This builder
 * has sensible default values so
 * @see AppConfig for all the default values.
 */
@Suppress("unused")
@ZirconDsl
class AppConfigBuilder private constructor(
    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    var blinkLengthInMilliSeconds: Long = 500,
    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    var isCursorBlinking: Boolean = false,
    /**
     * Can be used to control the availability of the clipboard.
     * Default is `true`
     */
    var isClipboardAvailable: Boolean = true,
    /**
     * The tileset to be used when no tileset is specified for the [Application].
     * Default is [CP437TilesetResources.wanderlust16x16]
     */
    var defaultTileset: TilesetResource = CP437TilesetResources.rogueYun16x16(),
    /**
     * The default graphical tileset to be used when no graphical tileset is specified.
     * Default is [GraphicalTilesetResources.nethack16x16]
     */
    var defaultGraphicalTileset: TilesetResource = GraphicalTilesetResources.nethack16x16(),
    /**
     * The default [ColorTheme] to be used when no color theme is specified.
     * Default is [ColorThemes.defaultTheme]
     */
    var defaultColorTheme: ColorTheme = ColorThemes.gamebookers(),
    /**
     * Can be used to switch debug mode on or off.
     * Default is `false`.
     */
    var debugMode: Boolean = false,
    /**
     * Controls the [Size] of the resulting tile grid when the [Application]
     * is started.
     * Default is `80x40`
     */
    var size: Size = Size.create(80, 40),
    /**
     * Controls if the [Application] will be full screen or not.
     * Default is `false`
     */
    var fullScreen: Boolean = false,
    /**
     * Controls if the [Application] will be borderless or not.
     * Default is `false`
     */
    var borderless: Boolean = false,
    /**
     * Sets the title of the application window.
     * Default is `"Zircon Application"`
     */
    var title: String = "Zircon Application",
    /**
     * Sets the fps limit of the resulting [Application].
     * Default is `60`
     */
    var fpsLimit: Int = 60,
    /**
     * Sets the [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    var debugConfig: DebugConfig = DebugConfig.defaultConfig(),
    /**
     * Determines the [CloseBehavior] when the application windows is closed.
     * Default is [CloseBehavior.EXIT_ON_CLOSE]
     * @see CloseBehavior
     */
    var closeBehavior: CloseBehavior = CloseBehavior.EXIT_ON_CLOSE,
    /**
     * Determines the [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    var shortcutsConfig: ShortcutsConfig = ShortcutsConfig.newBuilder().build(),
    /**
     * If set [iconData] contains the bytes of the icon image that will
     * be used in the application window.
     */
    var iconData: ByteArray? = null,
    /**
     * If set [iconPath] will contain the path of the resource that points
     * to an icon image that will be used in the application window.
     */
    var iconPath: String? = null,
    /**
     * If set, contains custom properties that plugin authors can set and access.
     */
    var customProperties: Map<AppConfigKey<*>, Any> = emptyMap(),
    /**
     * If set [tilesetFactories] will contain the list of [TilesetLoaders][TilesetLoader] to try to use
     * before using the default [TilesetLoader] of the [Renderer].
     */
    var tilesetFactories: Map<Pair<TileType, TilesetType>, TilesetFactory<*>> = mapOf(),
    /**
     * If set [modifierSupports] will contain the list of [TextureTransformer]s to try to use
     * before using the default [TextureTransformer] of the [Renderer].
     */
    var modifierSupports: Map<KClass<out TextureTransformModifier>, ModifierSupport<*>> = mapOf()
) : Builder<AppConfig> {

    fun withDebugConfig(debugConfig: DebugConfig) = also {
        this.debugConfig = debugConfig
    }

    fun withDebugMode(debugMode: Boolean) = also {
        this.debugMode = debugMode
    }

    fun withShortcutsConfig(shortcutsConfig: ShortcutsConfig) = also {
        this.shortcutsConfig = shortcutsConfig
    }

    fun withBlinkLengthInMilliSeconds(blinkLengthInMilliSeconds: Long) = also {
        this.blinkLengthInMilliSeconds = blinkLengthInMilliSeconds
    }

    fun withTitle(title: String) = also {
        this.title = title
    }

    @JvmOverloads
    fun withFullScreen(fullScreen: Boolean = true) = also {
        this.fullScreen = fullScreen
        this.borderless = fullScreen
    }

    @JvmOverloads
    fun withBorderless(borderless: Boolean = true) = also {
        this.borderless = borderless
    }

    fun withCursorBlinking(isCursorBlinking: Boolean) = also {
        this.isCursorBlinking = isCursorBlinking
    }

    fun withClipboardAvailable(isClipboardAvailable: Boolean) = also {
        this.isClipboardAvailable = isClipboardAvailable
    }

    fun withSize(width: Int, height: Int) = withSize(Size.create(width, height))

    fun withSize(size: Size) = also {
        this.size = size
    }

    fun withFpsLimit(fpsLimit: Int) = also {
        require(fpsLimit > 0) {
            "Can't set an fps limit which is less than zero"
        }
        this.fpsLimit = fpsLimit
    }

    fun withDefaultTileset(defaultTileset: TilesetResource) = also {
        this.defaultTileset = defaultTileset
    }

    fun withDefaultGraphicalTileset(defaultGraphicalTileset: TilesetResource) = also {
        this.defaultGraphicalTileset = defaultGraphicalTileset
    }

    fun withCloseBehavior(closeBehavior: CloseBehavior) = also {
        this.closeBehavior = closeBehavior
    }

    fun withIcon(iconData: ByteArray) = also {
        this.iconData = iconData
        this.iconPath = null
    }

    fun withIcon(iconPath: String) = also {
        this.iconPath = iconPath
        this.iconData = null
    }

    /**
     * Adds a custom property into the AppConfig object. This can later be retrieved using [AppConfig.get].
     *
     * ### End Developers
     *
     * You probably don't need to call this API.
     *
     * ### Plugin Developers
     *
     * Write extension methods off of [AppConfigBuilder] that call this API in order to enable end developers
     * to pass configuration in through [AppConfig] that your plugin can later use. It's recommended that [key]
     * be an `object` with minimal visibility (e.g. `internal`).
     *
     * @sample org.hexworks.zircon.api.application.AppConfigTest.propertyExample
     */
    fun <T : Any> withProperty(key: AppConfigKey<T>, value: T): AppConfigBuilder = also {
        customProperties = customProperties + (key to value)
    }

    /**
     * Sets custom [TilesetFactory] objects that will be used by the tileset loader. There can be only one
     * [TilesetFactory] for a combination of [TileType] + [TilesetType]
     */
    fun withTilesetFactories(vararg tilesetFactories: TilesetFactory<*>) = also {
        this.tilesetFactories =
            this.tilesetFactories + tilesetFactories.associateBy { it.supportedTileType to it.supportedTilesetType }
    }

    /**
     * Sets custom [TilesetFactory] objects that will be used by the tileset loader. There can be only one
     * [TilesetFactory] for a combination of [TileType] + [TilesetType]
     */
    fun withModifierSupports(vararg modifierSupports: ModifierSupport<*>) =
        also {
            this.modifierSupports = this.modifierSupports + modifierSupports.associateBy { it.modifierType }
        }

    override fun build() = AppConfig(
        blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
        isCursorBlinking = isCursorBlinking,
        isClipboardAvailable = isClipboardAvailable,
        defaultTileset = defaultTileset,
        defaultGraphicalTileset = defaultGraphicalTileset,
        defaultColorTheme = defaultColorTheme,
        debugMode = debugMode,
        size = size,
        fullScreen = fullScreen,
        borderless = borderless,
        title = title,
        fpsLimit = fpsLimit,
        debugConfig = debugConfig,
        closeBehavior = closeBehavior,
        shortcutsConfig = shortcutsConfig,
        iconData = iconData,
        iconPath = iconPath,
        customProperties = customProperties,
        tilesetLoaders = tilesetFactories,
        modifierSupports = modifierSupports
    ).also {
        RuntimeConfig.config = it
    }

    override fun createCopy() = AppConfigBuilder(
        blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
        isCursorBlinking = isCursorBlinking,
        isClipboardAvailable = isClipboardAvailable,
        defaultTileset = defaultTileset,
        defaultGraphicalTileset = defaultGraphicalTileset,
        defaultColorTheme = defaultColorTheme,
        debugMode = debugMode,
        size = size,
        fullScreen = fullScreen,
        borderless = borderless,
        title = title,
        fpsLimit = fpsLimit,
        debugConfig = debugConfig,
        closeBehavior = closeBehavior,
        shortcutsConfig = shortcutsConfig,
        iconData = iconData,
        iconPath = iconPath,
        customProperties = customProperties,
        tilesetFactories = tilesetFactories,
        modifierSupports = modifierSupports
    )

    companion object {

        @JvmStatic
        fun newBuilder() = AppConfigBuilder()

    }
}
