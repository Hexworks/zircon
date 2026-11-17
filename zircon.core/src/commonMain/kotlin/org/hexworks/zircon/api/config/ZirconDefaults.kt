package org.hexworks.zircon.api.config

import org.hexworks.zircon.api.builder.config.ZirconDefaultsBuilder
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.SystemThemeDetector
import org.hexworks.zircon.api.component.theme.platformSystemThemeDetector
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.component.theme.ComponentPalette

/**
 * Process-wide defaults: what Zircon uses when a builder is not told otherwise.
 * The current snapshot is [ZirconDefaults.current]; install a new one with
 * `zirconDefaults { ... }` before building anything that should pick it up.
 *
 * These are **not** per-grid settings. A component attached to a grid inherits that grid's
 * tileset, theme and palette from its parent; the defaults only apply to things built standalone
 * (graphics, layers, REXPaint imports, views) and to the debug and shortcut switches.
 */
public data class ZirconDefaults(
    /**
     * The tileset to be used when none is specified.
     * Default is [org.hexworks.zircon.api.CP437TilesetResources.rogueYun16x16]
     */
    val tileset: TilesetResource,
    /**
     * The [ComponentTheme] a [org.hexworks.zircon.api.screen.Screen] and every builder start
     * with. Default is [org.hexworks.zircon.api.ComponentThemes.default], the stock look.
     */
    val componentTheme: ComponentTheme,
    /**
     * The [ComponentPalette] a [org.hexworks.zircon.api.screen.Screen] and every builder start
     * with. Default is [org.hexworks.zircon.api.Palettes.catppuccin] (Mocha).
     */
    val componentPalette: ComponentPalette,
    /**
     * Tells [ThemeMode.SYSTEM][org.hexworks.zircon.api.component.theme.ThemeMode.SYSTEM] whether
     * the operating system is light or dark. Default is [platformSystemThemeDetector].
     */
    val systemThemeDetector: SystemThemeDetector,
    /**
     * Switches debug mode on or off.
     * Default is `false`.
     */
    val debugMode: Boolean,
    /**
     * The [DebugConfig] to be used when [debugMode] is `true`.
     * By default, all settings are `false`.
     */
    val debugConfig: DebugConfig,
    /**
     * The [ShortcutsConfig] to be used for built-in shortcuts.
     * @see ShortcutsConfig for defaults
     */
    val shortcutsConfig: ShortcutsConfig,
    /**
     * Receives the exceptions Zircon contains during a frame (a UI event listener, an
     * animation frame, a component renderer or a tile draw that threw) after they are logged.
     * Default is [ErrorHandler.NONE].
     * @see ErrorHandler
     */
    val onError: ErrorHandler,
) {

    /**
     * Tells whether bounds check should be performed or not.
     * This depends on the various debug mode configurations.
     */
    public fun shouldCheckBounds(): Boolean = !debugMode || !debugConfig.relaxBoundsCheck

    public companion object {

        /**
         * The defaults in effect. `zirconDefaults { ... }` replaces it.
         */
        public var current: ZirconDefaults = ZirconDefaultsBuilder().build()
    }
}
