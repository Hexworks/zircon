package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.interpolateTo
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.theme.PaletteKey.*
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * The colors of a screen: one [Color] per [PaletteKey], whether they are [dark][isDark], and
 * a sparse set of [overrides] for slots the theme's derivation got wrong for this palette.
 * A palette knows nothing about components; a [ComponentTheme] turns it into a
 * [ComponentStyleSet] per component through its [ThemeEntry.styles]. Theme and palette switch
 * independently, so one shape works with any color scheme.
 *
 * Build one with [componentPalette][org.hexworks.zircon.api.component.theme.componentPalette] or pick a bundled one from [org.hexworks.zircon.api.Palettes].
 * take a bundled one from [Palettes][org.hexworks.zircon.api.Palettes], or adapt a legacy
 */
public interface ComponentPalette {

    /**
     * A unique name, for display and debugging.
     */
    public val name: String

    /**
     * `true` for a dark scheme; derivations that blend may consult it.
     */
    public val isDark: Boolean

    /**
     * The color for [key].
     */
    public operator fun get(key: PaletteKey): Color

    /**
     * Colors that replace what the theme derived, per component slot. Empty by default.
     */
    public val overrides: Map<StyleOverrideKey, StyleSet>
        get() = emptyMap()

    /**
     * This palette with [PRIMARY] (and [ON_PRIMARY]) replaced: the palette author's default
     * accent is a suggestion, the user picks.
     */
    public fun withPrimary(color: Color, onColor: Color = this[ON_PRIMARY]): ComponentPalette

    /**
     * [styles] with this palette's [overrides] for the component applied, state by state.
     */
    public fun applyOverrides(
        styles: ComponentStyleSet,
        role: ComponentRole,
        variant: Variant = Variant.DEFAULT,
        part: Part = Part.NONE,
    ): ComponentStyleSet {
        if (overrides.isEmpty()) return styles
        fun styleFor(state: ComponentState): StyleSet =
            overrides[StyleOverrideKey(role, state, variant, part)]
                ?: overrides[StyleOverrideKey(role, state, Variant.DEFAULT, part)]
                ?: styles.fetchStyleFor(state)
        return componentStyleSet {
            defaultStyle = styleFor(ComponentState.DEFAULT)
            highlightedStyle = styleFor(ComponentState.HIGHLIGHTED)
            focusedStyle = styleFor(ComponentState.FOCUSED)
            activeStyle = styleFor(ComponentState.ACTIVE)
            disabledStyle = styleFor(ComponentState.DISABLED)
        }
    }
}

public val ComponentPalette.background: Color get() = this[BACKGROUND]
public val ComponentPalette.onBackground: Color get() = this[ON_BACKGROUND]
public val ComponentPalette.surface: Color get() = this[SURFACE]
public val ComponentPalette.onSurface: Color get() = this[ON_SURFACE]
public val ComponentPalette.surfaceRaised: Color get() = this[SURFACE_RAISED]
public val ComponentPalette.onSurfaceRaised: Color get() = this[ON_SURFACE_RAISED]
public val ComponentPalette.surfaceSunken: Color get() = this[SURFACE_SUNKEN]
public val ComponentPalette.textMuted: Color get() = this[TEXT_MUTED]
public val ComponentPalette.textDisabled: Color get() = this[TEXT_DISABLED]
public val ComponentPalette.border: Color get() = this[BORDER]
public val ComponentPalette.borderFocused: Color get() = this[BORDER_FOCUSED]
public val ComponentPalette.primary: Color get() = this[PRIMARY]
public val ComponentPalette.onPrimary: Color get() = this[ON_PRIMARY]
public val ComponentPalette.secondary: Color get() = this[SECONDARY]
public val ComponentPalette.onSecondary: Color get() = this[ON_SECONDARY]
public val ComponentPalette.accent: Color get() = this[ACCENT]
public val ComponentPalette.onAccent: Color get() = this[ON_ACCENT]
public val ComponentPalette.selection: Color get() = this[SELECTION]
public val ComponentPalette.onSelection: Color get() = this[ON_SELECTION]
public val ComponentPalette.info: Color get() = this[INFO]
public val ComponentPalette.onInfo: Color get() = this[ON_INFO]
public val ComponentPalette.success: Color get() = this[SUCCESS]
public val ComponentPalette.onSuccess: Color get() = this[ON_SUCCESS]
public val ComponentPalette.warning: Color get() = this[WARNING]
public val ComponentPalette.onWarning: Color get() = this[ON_WARNING]
public val ComponentPalette.error: Color get() = this[ERROR]
public val ComponentPalette.onError: Color get() = this[ON_ERROR]

/**
 * The status color pair a [Variant] selects, or `null` for the variants that are not a status
 * (`DEFAULT`, `PRIMARY`, `SECONDARY`, `LINK`).
 */
public fun ComponentPalette.statusColors(variant: Variant): Pair<Color, Color>? = when (variant) {
    Variant.INFO -> info to onInfo
    Variant.SUCCESS -> success to onSuccess
    Variant.WARNING -> warning to onWarning
    Variant.ERROR -> error to onError
    else -> null
}

/**
 * The emphasis color pair a [Variant] selects: `PRIMARY` for the default and link variants,
 * `SECONDARY` for the secondary one, the status pair for a status.
 */
public fun ComponentPalette.emphasisColors(variant: Variant): Pair<Color, Color> = when (variant) {
    Variant.SECONDARY -> secondary to onSecondary
    else -> statusColors(variant) ?: (primary to onPrimary)
}

/**
 * [from] blended [amount] (0..1) of the way toward [to], in linear RGB.
 */
public fun Color.blend(to: Color, amount: Double): Color = interpolateTo(to).getColorAtRatio(amount.coerceIn(0.0, 1.0))

/**
 * `true` when the color reads as dark (relative luminance below one half).
 */
public val Color.isDark: Boolean
    get() = (0.299 * red + 0.587 * green + 0.114 * blue) / 255.0 < 0.5
