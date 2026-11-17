package org.hexworks.zircon.api.component.theme

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.config.ZirconDefaults

/**
 * What the host wants right now: its light / dark [mode] and, when it has them, its exact
 * colors as a [palette]. [ThemeMode.SYSTEM] resolves through [mode]; `screen.followSystemPalette`
 * keeps a screen on [palette] (or on a light / dark pair of its choice when there is none).
 *
 * Process-wide, like [ZirconDefaults]. The renderers and the platforms write it through
 * [update]: the terminal renderer with the colors the terminal reports (`OSC 10/11/4`, mode
 * 2031), Compose with `isSystemInDarkTheme()`, a browser with `prefers-color-scheme`. Where
 * nothing pushes, [refresh] samples [ZirconDefaults.systemThemeDetector] (the desktop
 * setting); it runs once when this object is first touched, and the renderers call it every
 * couple of seconds when they have no better source. [palette] stays `null` until a host
 * reports colors (tmux and the desktop never do).
 *
 * Both values change on the thread that calls [update]: the renderer's loop thread, where
 * the frames are built, so a listener may touch components.
 */
public object SystemTheme {

    private val modeValue: Property<ThemeMode> = ThemeMode.DARK.toProperty()
    private val paletteValue: Property<ComponentPalette?> = null.toProperty()

    /**
     * [ThemeMode.LIGHT] or [ThemeMode.DARK], what the host reports now; never [ThemeMode.SYSTEM].
     * [ThemeMode.DARK] when nothing can tell.
     */
    public val modeProperty: ObservableValue<ThemeMode>
        get() = modeValue

    /**
     * The host's exact colors, `null` when it has none.
     */
    public val paletteProperty: ObservableValue<ComponentPalette?>
        get() = paletteValue

    /** The current value of [modeProperty]. */
    public val mode: ThemeMode
        get() = modeValue.value

    /** The current value of [paletteProperty]. */
    public val palette: ComponentPalette?
        get() = paletteValue.value

    init {
        installPlatformSystemThemeSource()
        refresh()
    }

    /**
     * What a renderer or a platform watcher learned: a new [mode], a new [palette], or both.
     * `null` leaves that value as it is; [reset] forgets a palette.
     */
    public fun update(mode: ThemeMode? = null, palette: ComponentPalette? = null) {
        require(mode != ThemeMode.SYSTEM) { "The system theme is LIGHT or DARK, never SYSTEM." }
        if (mode != null) modeValue.value = mode
        if (palette != null) paletteValue.value = palette
    }

    /**
     * Samples [ZirconDefaults.systemThemeDetector] and makes its answer the [mode]
     * ([ThemeMode.DARK] when it does not know). The fallback source for hosts that push nothing;
     * the desktop detectors shell out, so call it every few seconds at most, never per frame.
     */
    public fun refresh() {
        modeValue.value = ZirconDefaults.current.systemThemeDetector.detect() ?: ThemeMode.DARK
    }

    /**
     * Forgets the host palette and re-samples the mode with [refresh]: what a renderer that
     * reported colors does when it shuts down, and what a test does to start clean.
     */
    public fun reset() {
        paletteValue.value = null
        refresh()
    }
}

/**
 * Installs the platform's live source, if it has one: a browser listens for
 * `prefers-color-scheme` changes; the others have nothing to install and rely on the
 * renderers and [SystemTheme.refresh].
 */
internal expect fun installPlatformSystemThemeSource()
