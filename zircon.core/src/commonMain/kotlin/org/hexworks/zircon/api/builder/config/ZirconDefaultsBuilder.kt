@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.builder.config

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.SystemThemeDetector
import org.hexworks.zircon.api.component.theme.platformSystemThemeDetector
import org.hexworks.zircon.api.config.DebugConfig
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.config.ShortcutsConfig
import org.hexworks.zircon.api.config.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.toComponentPalette

public val DEFAULT_DEFAULT_TILESET: TilesetResource = CP437TilesetResources.rogueYun16x16()
public val DEFAULT_COLOR_THEME: ColorTheme = ColorThemes.gamebookers()
public val DEFAULT_COMPONENT_THEME: ComponentTheme = ComponentThemes.default()
public val DEFAULT_COMPONENT_PALETTE: ComponentPalette = DEFAULT_COLOR_THEME.toComponentPalette()
public const val DEFAULT_DEBUG_MODE: Boolean = false

/**
 * Builds [ZirconDefaults] snapshots. Starts from the built-in defaults, not from
 * [ZirconDefaults.current].
 * @see ZirconDefaults for what each setting does
 */
@ZirconDsl
public class ZirconDefaultsBuilder : Builder<ZirconDefaults> {

    /**
     * The tileset to be used when none is specified.
     * Default is [CP437TilesetResources.rogueYun16x16]
     */
    public var tileset: TilesetResource = DEFAULT_DEFAULT_TILESET

    /**
     * The legacy [ColorTheme]; [componentPalette] defaults to its adaptation.
     * Default is [ColorThemes.gamebookers]
     */
    @Deprecated("Colors come from the component palette; set `componentPalette`.")
    public var theme: ColorTheme = DEFAULT_COLOR_THEME

    /**
     * The [ComponentPalette] a screen and every builder start with. Default is [theme] adapted
     * with `toComponentPalette()`.
     */
    public var componentPalette: ComponentPalette? = null

    /**
     * The [ComponentTheme] a screen and every builder start with. Default is
     * [ComponentThemes.default], the stock look.
     */
    public var componentTheme: ComponentTheme = DEFAULT_COMPONENT_THEME

    /**
     * Tells [ThemeMode.SYSTEM][org.hexworks.zircon.api.component.theme.ThemeMode.SYSTEM] whether
     * the operating system is light or dark. Default is the platform's detector.
     */
    public var systemThemeDetector: SystemThemeDetector = platformSystemThemeDetector()

    /**
     * Switches debug mode on or off.
     * Default is `false`.
     */
    public var debugMode: Boolean = DEFAULT_DEBUG_MODE

    /**
     * The [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    public var debugConfig: DebugConfig = DEFAULT_DEBUG_CONFIG

    /**
     * The [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    public var shortcutsConfig: ShortcutsConfig = DEFAULT_SHORTCUTS_CONFIG

    /**
     * Receives the exceptions Zircon contains during a frame, after they are logged.
     * Default is [ErrorHandler.NONE].
     * @see ErrorHandler
     */
    public var onError: ErrorHandler = ErrorHandler.NONE

    /**
     * Configures [debugConfig] with the builder DSL.
     */
    public fun debug(init: DebugConfigBuilder.() -> Unit) {
        debugConfig = DebugConfigBuilder().apply(init).build()
    }

    /**
     * Configures [shortcutsConfig] with the builder DSL.
     */
    public fun shortcuts(init: ShortcutsConfigBuilder.() -> Unit) {
        shortcutsConfig = ShortcutsConfigBuilder().apply(init).build()
    }

    override fun build(): ZirconDefaults = ZirconDefaults(
        tileset = tileset,
        theme = theme,
        componentTheme = componentTheme,
        componentPalette = componentPalette ?: theme.toComponentPalette(),
        systemThemeDetector = systemThemeDetector,
        debugMode = debugMode,
        debugConfig = debugConfig,
        shortcutsConfig = shortcutsConfig,
        onError = onError,
    )
}

/**
 * Builds a [ZirconDefaults] snapshot and installs it as [ZirconDefaults.current].
 * Call it once at startup, before building anything that should pick the defaults up.
 */
public fun zirconDefaults(init: ZirconDefaultsBuilder.() -> Unit): ZirconDefaults =
    ZirconDefaultsBuilder().apply(init).build().also { ZirconDefaults.current = it }
