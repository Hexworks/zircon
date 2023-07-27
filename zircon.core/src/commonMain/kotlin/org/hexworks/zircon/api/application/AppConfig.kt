package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.jvm.JvmName
import kotlin.reflect.KClass

/**
 * Object that encapsulates the configuration parameters for an [Application].
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
data class AppConfig internal constructor(
    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    val blinkLengthInMilliSeconds: Long,
    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    val isCursorBlinking: Boolean,
    /**
     * Can be used to control the availability of the clipboard.
     * Default is `true`
     */
    val isClipboardAvailable: Boolean,

    /**
     * The tileset to be used when no tileset is specified for the [Application].
     * Default is [CP437TilesetResources.wanderlust16x16]
     */
    val defaultTileset: TilesetResource,
    /**
     * The default graphical tileset to be used when no graphical tileset is specified.
     * Default is [GraphicalTilesetResources.nethack16x16]
     */
    val defaultGraphicalTileset: TilesetResource,
    /**
     * The default [ColorTheme] to be used when no color theme is specified.
     * Default is [ColorThemes.defaultTheme]
     */
    val defaultColorTheme: ColorTheme,
    /**
     * Can be used to switch debug mode on or off.
     * Default is `false`.
     */
    val debugMode: Boolean,
    /**
     * Controls the [Size] of the resulting tile grid when the [Application]
     * is started.
     * Default is `80x40`
     */
    val size: Size,
    /**
     * Controls if the [Application] will be full screen or not.
     * Default is `false`
     */
    val fullScreen: Boolean,
    /**
     * Sets the title of the application window.
     * Default is `"Zircon Application"`
     */
    val title: String,
    /**
     * If set [iconPath] will contain the path of the resource that points
     * to an icon image that will be used in the application window.
     */
    val iconPath: String?,
    /**
     * Sets the [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    val debugConfig: DebugConfig,
    /**
     * Determines the [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    val shortcutsConfig: ShortcutsConfig,
    /**
     * Contains all the [TilesetFactory] objects that will be used by the
     * [TilesetLoader] when a renderer is created.
     */
    val tilesetFactories: List<TilesetFactory<*>>,
    /**
     * If set [textureModifierStrategies] will contain the list of [TextureTransformer]s to try to use
     * before using the default [TextureTransformer] of the [Renderer].
     */
    val textureModifierStrategies: Map<KClass<out TextureModifier>, TextureModifierStrategy<*, *>>,
    /**
     * If set, contains custom properties that plugin authors can set and access.
     */
    internal val customProperties: Map<AppConfigKey<*>, Any>,
) {

    /**
     * Tells whether bounds check should be performed or not.
     * This depends on the various debug mode configurations.
     */
    fun shouldCheckBounds() = !debugMode || !debugConfig.relaxBoundsCheck

    /**
     * Retrieves a custom property set earlier using [AppConfigBuilder.customProperties]. If this property was
     * never set, this returns `null`.
     *
     * **Note that** you probably don't need to call this API.
     */
    fun <T : Any> getOrNull(key: AppConfigKey<T>): T? {
        val value: Any? = customProperties[key]
        // This is actually a safe cast because of the way `withProperty` is defined.
        @Suppress("UNCHECKED_CAST")
        return value as T?
    }

    /**
     * Retrieves a custom property set earlier using [AppConfigBuilder.customProperties]. If this property was
     * never set, this returns the result of calling `orElse`.
     *
     * **Note that** you probably don't need to call this API.
     */
    fun <T : Any> getOrElse(key: AppConfigKey<T>, orElse: (key: AppConfigKey<T>) -> T): T {
        return getOrNull(key) ?: orElse(key)
    }

    companion object {

        fun defaultAppConfig() = AppConfigBuilder().build()

    }
}

/**
 * Creates a new [AppConfig] using the builder DSL and returns it.
 */
fun appConfig(init: AppConfigBuilder.() -> Unit = {}): AppConfig =
    AppConfigBuilder().apply(init).build()

@JvmName("filterTilesetsByType")
@Suppress("UNCHECKED_CAST")
fun <T : Any> Map<Pair<TileType, TilesetType>, TilesetFactory<*>>.filterByType(type: KClass<T>): Map<Pair<TileType, TilesetType>, TilesetFactory<T>> {
    return this.filterValues { it.targetType == type } as Map<Pair<TileType, TilesetType>, TilesetFactory<T>>
}

@JvmName("filterModifiersByType")
@Suppress("UNCHECKED_CAST")
fun <T : Any, C : Any> Map<KClass<out TextureModifier>, TextureModifierStrategy<*, *>>.filterByType(type: KClass<T>): Map<KClass<out TextureModifier>, TextureModifierStrategy<T, C>> {
    return this.filterValues { it.targetType == type } as Map<KClass<out TextureModifier>, TextureModifierStrategy<T, C>>
}
