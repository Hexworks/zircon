package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.application.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.application.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.renderer.Renderer
import kotlin.reflect.KClass


public const val DEFAULT_BLINK_LENGTH: Long = 500L
public const val DEFAULT_IS_CURSOR_BLINKING: Boolean = false
public const val DEFAULT_CLIPBOARD_AVAILABLE: Boolean = false
public val DEFAULT_TILESET: TilesetResource = CP437TilesetResources.rogueYun16x16()
public val DEFAULT_GRAPHICAL_TILESET: TilesetResource = GraphicalTilesetResources.nethack16x16()
public val DEFAULT_COLOR_THEME: ColorTheme = ColorThemes.gamebookers()
public const val DEFAULT_DEBUG_MODE: Boolean = false
public val DEFAULT_SIZE: Size = Size(80, 40)
public const val DEFAULT_FULL_SCREEN: Boolean = false
public const val DEFAULT_TITLE: String = "Zircon Application"

/**
 * This *builder* class can be used to build [AppConfig] instances. This builder
 * has sensible default values so
 * @see AppConfig for all the default values.
 */
@ZirconDsl
public class AppConfigBuilder : Builder<AppConfig> {

    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    public var blinkLengthInMilliSeconds: Long = DEFAULT_BLINK_LENGTH

    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    public var isCursorBlinking: Boolean = DEFAULT_IS_CURSOR_BLINKING

    /**
     * Can be used to control the availability of the clipboard.
     * Default is `true`
     */
    public var isClipboardAvailable: Boolean = DEFAULT_CLIPBOARD_AVAILABLE

    /**
     * The tileset to be used when no tileset is specified for the [Application].
     * Default is [CP437TilesetResources.wanderlust16x16]
     */
    public var defaultTileset: TilesetResource = DEFAULT_TILESET

    /**
     * The default graphical tileset to be used when no graphical tileset is specified.
     * Default is [GraphicalTilesetResources.nethack16x16]
     */
    public var defaultGraphicalTileset: TilesetResource = DEFAULT_GRAPHICAL_TILESET

    /**
     * The default [ColorTheme] to be used when no color theme is specified.
     * Default is [ColorThemes.defaultTheme]
     */
    public var defaultColorTheme: ColorTheme = DEFAULT_COLOR_THEME

    /**
     * Can be used to switch debug mode on or off.
     * Default is `false`.
     */
    public var debugMode: Boolean = DEFAULT_DEBUG_MODE

    /**
     * Controls the [Size] of the resulting tile grid when the [Application]
     * is started.
     * Default is `80x40`
     */
    public var size: Size = DEFAULT_SIZE

    /**
     * Controls if the [Application] will be full screen or not.
     * Default is `false`
     */
    public var fullScreen: Boolean = DEFAULT_FULL_SCREEN

    /**
     * Sets the title of the application window.
     * Default is `"Zircon Application"`
     */
    public var title: String = DEFAULT_TITLE

    /**
     * If set [iconPath] will contain the path of the resource that points
     * to an icon image that will be used in the application window.
     */
    public var iconPath: String? = null

    /**
     * Sets the [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    public var debugConfig: DebugConfig = DEFAULT_DEBUG_CONFIG

    /**
     * Determines the [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    public var shortcutsConfig: ShortcutsConfig = DEFAULT_SHORTCUTS_CONFIG

    /**
     * If set [tilesetFactories] will contain the list of [TilesetLoaders][TilesetLoader] to try to use
     * before using the default [TilesetLoader] of the [Renderer].
     */
    public var tilesetFactories: MutableMap<Pair<TileType, TilesetType>, TilesetFactory<*>> =
        mutableMapOf()

    /**
     * If set [textureModifierSupports] will contain the list of [TextureTransformer]s to try to use
     * before using the default [TextureTransformer] of the [Renderer].
     */
    public var textureModifierSupports: MutableMap<KClass<out TextureModifier>, TextureModifierStrategy<*, *>> = mutableMapOf()

    /**
     * If set, contains custom properties that plugin authors can set and access.
     */
    internal val customProperties = mutableMapOf<AppConfigKey<*>, Any>()

    override fun build(): AppConfig = AppConfig(
        blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
        isCursorBlinking = isCursorBlinking,
        isClipboardAvailable = isClipboardAvailable,
        defaultTileset = defaultTileset,
        defaultGraphicalTileset = defaultGraphicalTileset,
        defaultColorTheme = defaultColorTheme,
        debugMode = debugMode,
        size = size,
        fullScreen = fullScreen,
        title = title,
        debugConfig = debugConfig,
        shortcutsConfig = shortcutsConfig,
        iconPath = iconPath,
        customProperties = customProperties,
        tilesetFactories = tilesetFactories.values.toList(),
        textureModifierStrategies = textureModifierSupports
    ).also {
        RuntimeConfig.config = it
    }
}

public fun appConfig(init: AppConfigBuilder.() -> Unit): AppConfig {
    return AppConfigBuilder().apply {

    }.apply(init).build()
}

/**
 * Adds a custom property to the AppConfig object. This can later be retrieved using [AppConfig.getOrElse]
 * or [AppConfig.getOrNull].
 *
 * ### App Developers
 *
 * You probably don't need to call this API.
 *
 * ### Plugin Developers
 *
 * Write extension methods off of [AppConfigBuilder] that call this API in order to enable end developers
 * to pass configuration in through [AppConfig] that your plugin can later use. It's recommended that `key`
 * be an `object` with minimal visibility (e.g. `internal`).
 *
 * @sample org.hexworks.zircon.api.application.AppConfigTest
 */
public fun <T : Any> AppConfigBuilder.customProperty(init: CustomPropertyBuilder<T>.() -> Unit) {
    val (key, value) = CustomPropertyBuilder<T>().apply(init).build()
    customProperties[key] = value
}

public fun AppConfigBuilder.shortcutsConfig(init: ShortcutsConfigBuilder.() -> Unit) {
    shortcutsConfig = ShortcutsConfigBuilder().apply(init).build()
}

public fun AppConfigBuilder.debugConfig(init: DebugConfigBuilder.() -> Unit) {
    debugConfig = DebugConfigBuilder().apply(init).build()
}

/**
 * Sets a custom [TilesetFactory] that will be used by the tileset loader. There can be only one
 * [TilesetFactory] for a combination of [TileType] + [TilesetType]
 */
public fun <S : Any> AppConfigBuilder.tilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit) {
    val result = TilesetFactoryBuilder<S>().apply(init).build()
    tilesetFactories[result.supportedTileType to result.supportedTilesetType] = result
}

/**
 * Adds support for the given texture modifier.
 * 📘 *Note that* you don't need to add support for [TileModifier]s as they are self-contained.
 */
public fun <T : Any, C : Any> AppConfigBuilder.textureModifier(init: TextureModifierBuilder<T, C>.() -> Unit) {
    val result = TextureModifierBuilder<T, C>().apply(init).build()
    textureModifierSupports[result.modifierType] = result
}

