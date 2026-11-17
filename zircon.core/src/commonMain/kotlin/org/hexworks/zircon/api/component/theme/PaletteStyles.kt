package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ComponentStyleSet

/**
 * The derivations the default theme uses to turn a [ComponentPalette] into a component's
 * colors per state, for other themes to reuse in their [ThemeEntry.styles]. A theme that
 * wants a different hover (a hard swap, a shadow shift) writes its own.
 */
public object PaletteStyles {

    /**
     * A container: the frame glyphs in [PaletteKey.BORDER] on [PaletteKey.SURFACE]
     * ([PaletteKey.BORDER_FOCUSED] while focused), disabled text when disabled.
     */
    public val surface: (ComponentPalette) -> ComponentStyleSet = { palette ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = palette.border; backgroundColor = palette.surface }
            focusedStyle = styleSet { foregroundColor = palette.borderFocused; backgroundColor = palette.surface }
            disabledStyle = styleSet { foregroundColor = palette.textDisabled; backgroundColor = palette.surface }
        }
    }

    /**
     * The screen itself: [PaletteKey.ON_BACKGROUND] on [PaletteKey.BACKGROUND].
     */
    public val background: (ComponentPalette) -> ComponentStyleSet = { palette ->
        flat(palette.onBackground, palette.background, palette.textDisabled)
    }

    /**
     * A popup: [PaletteKey.ON_SURFACE_RAISED] on [PaletteKey.SURFACE_RAISED].
     */
    public val raised: (ComponentPalette) -> ComponentStyleSet = { palette ->
        flat(palette.onSurfaceRaised, palette.surfaceRaised, palette.textDisabled)
    }

    /**
     * Text in [key] on whatever is behind it; [PaletteKey.TEXT_DISABLED] when disabled.
     */
    public fun text(key: PaletteKey = PaletteKey.ON_SURFACE): (ComponentPalette) -> ComponentStyleSet = { palette ->
        flat(palette[key], Color.TRANSPARENT, palette.textDisabled)
    }

    /**
     * Something clickable, in the emphasis colors of [variant]: the color as text at rest, its
     * `ON_*` text on the color while focused, on a [hovered] wash of it while hovered and on a
     * [pressed] deepening of it while pressed. The three filled states keep the same text
     * color, so they all read; the background tells them apart.
     */
    public fun interactive(variant: Variant = Variant.DEFAULT): (ComponentPalette) -> ComponentStyleSet = { palette ->
        val (color, onColor) = palette.emphasisColors(variant)
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = color; backgroundColor = Color.TRANSPARENT }
            highlightedStyle = styleSet { foregroundColor = onColor; backgroundColor = palette.hovered(color) }
            focusedStyle = styleSet { foregroundColor = onColor; backgroundColor = color }
            activeStyle = styleSet { foregroundColor = onColor; backgroundColor = pressed(color, onColor) }
            disabledStyle = styleSet { foregroundColor = palette.textDisabled; backgroundColor = Color.TRANSPARENT }
        }
    }

    /**
     * [color] as a hover surface: washed [HOVER_WASH] of the way toward [PaletteKey.SURFACE], so
     * it sits between the resting and the focused look.
     */
    public fun ComponentPalette.hovered(color: Color): Color = color.blend(surface, HOVER_WASH)

    /**
     * [color] as a pressed surface: pushed [PRESS_INK] of the way away from [onColor], the text
     * drawn on it (toward white under dark text, toward black under light text), so the press
     * is visibly stronger than focus and the text contrasts more, not less.
     */
    public fun pressed(color: Color, onColor: Color): Color = color.blend(if (onColor.isDark) WHITE else BLACK, PRESS_INK)

    /** How far a hovered surface blends toward the surface. */
    public const val HOVER_WASH: Double = 0.3

    /** How far a pressed surface blends away from its text color. */
    public const val PRESS_INK: Double = 0.5

    private val WHITE: Color = Color.create(255, 255, 255)
    private val BLACK: Color = Color.create(0, 0, 0)

    /**
     * An input field: a sunken well at rest, the selection colors while focused. Hovered and
     * pressed look focused too, so a focused field does not flicker under the mouse.
     */
    public val field: (ComponentPalette) -> ComponentStyleSet = { palette ->
        val focused = styleSet { foregroundColor = palette.onSelection; backgroundColor = palette.selection }
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = palette.onBackground; backgroundColor = palette.surfaceSunken }
            highlightedStyle = focused
            focusedStyle = focused
            activeStyle = focused
            disabledStyle = styleSet { foregroundColor = palette.textDisabled; backgroundColor = palette.surfaceSunken }
        }
    }

    /**
     * A selected thing: [PaletteKey.ON_SELECTION] on [PaletteKey.SELECTION] in every state.
     */
    public val selection: (ComponentPalette) -> ComponentStyleSet = { palette ->
        flat(palette.onSelection, palette.selection, palette.textDisabled)
    }

    /**
     * A status surface in the colors of [variant] (`INFO`, `SUCCESS`, `WARNING`, `ERROR`):
     * `ON_*` on the status color; the primary pair for the other variants.
     */
    public fun status(variant: Variant): (ComponentPalette) -> ComponentStyleSet = { palette ->
        val (color, onColor) = palette.emphasisColors(variant)
        flat(onColor, color, palette.textDisabled)
    }

    /**
     * The same [foreground] on [background] in every state but disabled.
     */
    public fun flat(foreground: Color, background: Color, disabledForeground: Color = foreground): ComponentStyleSet =
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = foreground; backgroundColor = background }
            disabledStyle = styleSet { foregroundColor = disabledForeground; backgroundColor = background }
        }
}
