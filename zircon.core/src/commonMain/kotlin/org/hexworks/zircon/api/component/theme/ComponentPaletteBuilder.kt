package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.extensions.desaturate
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.theme.PaletteKey.*
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.component.theme.DefaultComponentPalette

/**
 * Builds a [ComponentPalette]. [background], [onBackground] and [primary] are required; every
 * other key defaults to a blend of what is set (the surface ladder steps away from the
 * background, text steps toward it, `ON_*` of an emphasis color is the background), so a
 * palette can start from three colors and name the rest as it grows.
 */
@ZirconDsl
public class ComponentPaletteBuilder : Builder<ComponentPalette> {

    public var name: String = "custom"

    /**
     * Dark or light; defaults to what [background] looks like.
     */
    public var isDark: Boolean? = null

    public var background: Color? = null
    public var onBackground: Color? = null
    public var surface: Color? = null
    public var onSurface: Color? = null
    public var surfaceRaised: Color? = null
    public var onSurfaceRaised: Color? = null
    public var surfaceSunken: Color? = null
    public var textMuted: Color? = null
    public var textDisabled: Color? = null
    public var border: Color? = null
    public var borderFocused: Color? = null
    public var primary: Color? = null
    public var onPrimary: Color? = null
    public var secondary: Color? = null
    public var onSecondary: Color? = null
    public var accent: Color? = null
    public var onAccent: Color? = null
    public var selection: Color? = null
    public var onSelection: Color? = null
    public var info: Color? = null
    public var onInfo: Color? = null
    public var success: Color? = null
    public var onSuccess: Color? = null
    public var warning: Color? = null
    public var onWarning: Color? = null
    public var error: Color? = null
    public var onError: Color? = null

    private val overrides = mutableMapOf<StyleOverrideKey, StyleSet>()

    /**
     * Replaces the colors the theme derives for one component slot.
     */
    public fun override(
        role: ComponentRole,
        state: ComponentState,
        variant: Variant = Variant.DEFAULT,
        part: Part = Part.NONE,
        init: StyleSetBuilder.() -> Unit,
    ) {
        overrides[StyleOverrideKey(role, state, variant, part)] = styleSet(init)
    }

    /**
     * Replaces the colors the theme derives for one component in every state.
     */
    public fun override(
        role: ComponentRole,
        variant: Variant = Variant.DEFAULT,
        part: Part = Part.NONE,
        init: StyleSetBuilder.() -> Unit,
    ) {
        ComponentState.entries.forEach { override(role, it, variant, part, init) }
    }

    override fun build(): ComponentPalette {
        val background = requireNotNull(background) { "A palette needs a background color." }
        val onBackground = requireNotNull(onBackground) { "A palette needs an onBackground color." }
        val primary = requireNotNull(primary) { "A palette needs a primary color." }
        val dark = isDark ?: background.isDark
        // A step up is lighter on a dark scheme and darker on a light one.
        val up = if (dark) WHITE else BLACK
        val down = if (dark) BLACK else WHITE
        val surface = surface ?: background.blend(up, STEP)
        val onSurface = onSurface ?: onBackground
        val surfaceRaised = surfaceRaised ?: surface.blend(up, STEP)
        val textMuted = textMuted ?: onBackground.blend(background, MUTED)
        val onPrimary = onPrimary ?: background
        val secondary = secondary ?: primary.desaturate(0.5)
        val accent = accent ?: primary
        val colors = mapOf(
            BACKGROUND to background,
            ON_BACKGROUND to onBackground,
            SURFACE to surface,
            ON_SURFACE to onSurface,
            SURFACE_RAISED to surfaceRaised,
            ON_SURFACE_RAISED to (onSurfaceRaised ?: onSurface),
            SURFACE_SUNKEN to (surfaceSunken ?: background.blend(down, STEP)),
            TEXT_MUTED to textMuted,
            TEXT_DISABLED to (textDisabled ?: textMuted.blend(background, MUTED)),
            BORDER to (border ?: textMuted),
            BORDER_FOCUSED to (borderFocused ?: primary),
            PRIMARY to primary,
            ON_PRIMARY to onPrimary,
            SECONDARY to secondary,
            ON_SECONDARY to (onSecondary ?: onPrimary),
            ACCENT to accent,
            ON_ACCENT to (onAccent ?: onPrimary),
            SELECTION to (selection ?: primary.blend(background, SELECTION_WASH)),
            ON_SELECTION to (onSelection ?: onBackground),
            INFO to (info ?: DEFAULT_INFO),
            ON_INFO to (onInfo ?: onPrimary),
            SUCCESS to (success ?: DEFAULT_SUCCESS),
            ON_SUCCESS to (onSuccess ?: onPrimary),
            WARNING to (warning ?: DEFAULT_WARNING),
            ON_WARNING to (onWarning ?: onPrimary),
            ERROR to (error ?: DEFAULT_ERROR),
            ON_ERROR to (onError ?: onPrimary),
        )
        return DefaultComponentPalette(name, dark, colors, overrides.toMap())
    }

    public companion object {
        /** One surface step: how far a step blends toward white or black. */
        public const val STEP: Double = 0.12
        /** How far muted text blends toward the background. */
        public const val MUTED: Double = 0.35
        /** How far the selection blends from primary toward the background. */
        public const val SELECTION_WASH: Double = 0.4
        public val DEFAULT_INFO: Color = Color.fromString("#3b82f6")
        public val DEFAULT_SUCCESS: Color = Color.fromString("#22c55e")
        public val DEFAULT_WARNING: Color = Color.fromString("#eab308")
        public val DEFAULT_ERROR: Color = Color.fromString("#ef4444")
        private val WHITE: Color = Color.create(255, 255, 255)
        private val BLACK: Color = Color.create(0, 0, 0)
    }
}

/**
 * Builds a [ComponentPalette] with the builder DSL.
 */
public fun componentPalette(init: ComponentPaletteBuilder.() -> Unit): ComponentPalette =
    ComponentPaletteBuilder().apply(init).build()
