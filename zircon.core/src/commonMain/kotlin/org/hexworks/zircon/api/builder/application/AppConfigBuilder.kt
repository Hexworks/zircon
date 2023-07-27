package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.application.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.application.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.application.TilesetFactoryBuilder.Companion.DEFAULT_TILESET_FACTORIES
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.reflect.KClass


const val DEFAULT_BLINK_LENGTH = 500L
const val DEFAULT_IS_CURSOR_BLINKING = false
const val DEFAULT_CLIPBOARD_AVAILABLE = false
val DEFAULT_TILESET = CP437TilesetResources.rogueYun16x16()
val DEFAULT_GRAPHICAL_TILESET = GraphicalTilesetResources.nethack16x16()
val DEFAULT_COLOR_THEME = ColorThemes.gamebookers()
const val DEFAULT_DEBUG_MODE = false
val DEFAULT_SIZE = Size.create(80, 40)
const val DEFAULT_FULL_SCREEN = false
const val DEFAULT_TITLE = "Zircon Application"

/**
 * This *builder* class can be used to build [AppConfig] instances. This builder
 * has sensible default values so
 * @see AppConfig for all the default values.
 */
@ZirconDsl
class AppConfigBuilder : Builder<AppConfig> {

    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    var blinkLengthInMilliSeconds: Long = DEFAULT_BLINK_LENGTH

    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    var isCursorBlinking: Boolean = DEFAULT_IS_CURSOR_BLINKING

    /**
     * Can be used to control the availability of the clipboard.
     * Default is `true`
     */
    var isClipboardAvailable: Boolean = DEFAULT_CLIPBOARD_AVAILABLE

    /**
     * The tileset to be used when no tileset is specified for the [Application].
     * Default is [CP437TilesetResources.wanderlust16x16]
     */
    var defaultTileset: TilesetResource = DEFAULT_TILESET

    /**
     * The default graphical tileset to be used when no graphical tileset is specified.
     * Default is [GraphicalTilesetResources.nethack16x16]
     */
    var defaultGraphicalTileset: TilesetResource = DEFAULT_GRAPHICAL_TILESET

    /**
     * The default [ColorTheme] to be used when no color theme is specified.
     * Default is [ColorThemes.defaultTheme]
     */
    var defaultColorTheme: ColorTheme = DEFAULT_COLOR_THEME

    /**
     * Can be used to switch debug mode on or off.
     * Default is `false`.
     */
    var debugMode: Boolean = DEFAULT_DEBUG_MODE

    /**
     * Controls the [Size] of the resulting tile grid when the [Application]
     * is started.
     * Default is `80x40`
     */
    var size: Size = DEFAULT_SIZE

    /**
     * Controls if the [Application] will be full screen or not.
     * Default is `false`
     */
    var fullScreen: Boolean = DEFAULT_FULL_SCREEN

    /**
     * Sets the title of the application window.
     * Default is `"Zircon Application"`
     */
    var title: String = DEFAULT_TITLE

    /**
     * If set [iconPath] will contain the path of the resource that points
     * to an icon image that will be used in the application window.
     */
    var iconPath: String? = null

    /**
     * Sets the [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    var debugConfig: DebugConfig = DEFAULT_DEBUG_CONFIG

    /**
     * Determines the [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    var shortcutsConfig: ShortcutsConfig = DEFAULT_SHORTCUTS_CONFIG

    /**
     * If set, contains custom properties that plugin authors can set and access.
     */
    internal val customProperties = mutableMapOf<AppConfigKey<*>, Any>()

    /**
     * If set [tilesetFactories] will contain the list of [TilesetLoaders][TilesetLoader] to try to use
     * before using the default [TilesetLoader] of the [Renderer].
     */
    internal val tilesetFactories: MutableMap<Pair<TileType, TilesetType>, TilesetFactory<*>> =
        DEFAULT_TILESET_FACTORIES.associateBy { it.supportedTileType to it.supportedTilesetType }.toMutableMap()

    /**
     * If set [textureModifierSupports] will contain the list of [TextureTransformer]s to try to use
     * before using the default [TextureTransformer] of the [Renderer].
     */
    internal val textureModifierSupports = mutableMapOf<KClass<out TextureModifier>, TextureModifierStrategy<*, *>>()

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

fun appConfig(init: AppConfigBuilder.() -> Unit): AppConfig {
    return AppConfigBuilder().apply(init).build()
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
fun <T : Any> AppConfigBuilder.customProperty(init: CustomPropertyBuilder<T>.() -> Unit) {
    val (key, value) = CustomPropertyBuilder<T>().apply(init).build()
    customProperties[key] = value
}

fun AppConfigBuilder.shortcutsConfig(init: ShortcutsConfigBuilder.() -> Unit) {
    shortcutsConfig = ShortcutsConfigBuilder().apply(init).build()
}

fun AppConfigBuilder.debugConfig(init: DebugConfigBuilder.() -> Unit) {
    debugConfig = DebugConfigBuilder().apply(init).build()
}

/**
 * Sets a custom [TilesetFactory] that will be used by the tileset loader. There can be only one
 * [TilesetFactory] for a combination of [TileType] + [TilesetType]
 */
fun <S : Any> AppConfigBuilder.tilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit) {
    val result = TilesetFactoryBuilder<S>().apply(init).build()
    tilesetFactories[result.supportedTileType to result.supportedTilesetType] = result
}

/**
 * Adds support for the given texture modifier.
 * 📘 *Note that* you don't need to add support for [TileModifier]s as they are self-contained.
 */
fun <T : Any, C : Any> AppConfigBuilder.textureModifier(init: TextureModifierBuilder<T, C>.() -> Unit) {
    val result = TextureModifierBuilder<T, C>().apply(init).build()
    textureModifierSupports[result.modifierType] = result
}

fun AppConfigBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}
