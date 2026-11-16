package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentRole.*
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ThemeEntry
import org.hexworks.zircon.api.component.theme.ThemeKey
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.Variant.*
import org.hexworks.zircon.api.component.theme.accent
import org.hexworks.zircon.api.component.theme.blend
import org.hexworks.zircon.api.component.theme.emphasisColors
import org.hexworks.zircon.api.component.theme.onPrimary
import org.hexworks.zircon.api.component.theme.onSelection
import org.hexworks.zircon.api.component.theme.onSurface
import org.hexworks.zircon.api.component.theme.primary
import org.hexworks.zircon.api.component.theme.selection
import org.hexworks.zircon.api.component.theme.surface
import org.hexworks.zircon.api.component.theme.textMuted
import org.hexworks.zircon.api.graphics.BoxType

/**
 * The Neobrutalism theme: heavy borders, hard offset shadows, flat fills. Shape only; the
 * colors come from the screen's [ComponentPalette] (`Palettes.neobrutalism(mode)` has
 * HyperUI's). The hover of a Neobrutalist control is a hard swap to the accent, not a blend.
 * See `docs/neo-brutalism.md` for the glyph mapping.
 */
internal object NeobrutalismTheme : ComponentTheme {

    override val name: String = "Neobrutalism"

    // Decorations, outermost first (the order DefaultComponentRenderingStrategy renders them).
    private fun framed(shadow: NeoShadow, hoverShift: Boolean = false, divider: Boolean = false, pad: Int = 1): List<ComponentDecorationRenderer> =
        listOf(NeoFrameDecoration(shadow, BoxType.HEAVY, hoverShift, divider)) + if (pad > 0) listOf(margin(0, pad)) else emptyList()

    private val transparent = Color.TRANSPARENT

    private fun flat(foreground: Color, background: Color): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = foreground; backgroundColor = background }
    }

    private val surface: (ComponentPalette) -> ComponentStyleSet = { flat(it.onSurface, it.surface) }
    private val text: (ComponentPalette) -> ComponentStyleSet = { flat(it.onSurface, transparent) }
    private val mutedText: (ComponentPalette) -> ComponentStyleSet = { flat(it.textMuted, transparent) }
    private val accentBar: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onPrimary; backgroundColor = p.primary }
            highlightedStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            focusedStyle = highlightedStyle
            activeStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = p.surface }
        }
    }
    private val interactive: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.surface }
            highlightedStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            focusedStyle = styleSet { foregroundColor = p.onPrimary; backgroundColor = p.primary }
            activeStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = p.surface }
        }
    }
    private val interactiveOnSurface: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = transparent }
            highlightedStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            focusedStyle = styleSet { foregroundColor = p.onPrimary; backgroundColor = p.primary }
            activeStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = transparent }
        }
    }
    private val link: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.surface }
            highlightedStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            focusedStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            activeStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = p.surface }
        }
    }
    private val inverted: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            highlightedStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            focusedStyle = styleSet { foregroundColor = p.onPrimary; backgroundColor = p.primary }
            activeStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.accent }
            disabledStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.textMuted }
        }
    }
    // The same for every interactive state: a field must not flicker on hover while focused.
    private val field: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.surface }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = p.surface }
        }
    }
    private val tab: (ComponentPalette) -> ComponentStyleSet = { p ->
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = p.onSurface; backgroundColor = p.surface }
            highlightedStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            focusedStyle = styleSet { foregroundColor = p.onPrimary; backgroundColor = p.primary }
            activeStyle = styleSet { foregroundColor = p.surface; backgroundColor = p.onSurface }
            disabledStyle = styleSet { foregroundColor = p.textMuted; backgroundColor = p.surface }
        }
    }

    /**
     * HyperUI's alerts are a pale wash of the status color with the status color darkened for
     * the text: a blend of the status pair toward the surface and toward the surface's text.
     */
    private fun alertColors(palette: ComponentPalette, variant: Variant): Pair<Color, Color> {
        val (color, _) = palette.emphasisColors(variant)
        return color.blend(palette.surface, WASH) to color.blend(palette.onSurface, INK)
    }

    private fun alert(variant: Variant) = ThemeEntry(
        decorationRenderers = framed(NeoShadow.THICK),
        styles = { p -> alertColors(p, variant).let { (bg, fg) -> flat(fg, bg) } },
    )

    private fun alertText(variant: Variant) = ThemeEntry(
        componentRenderer = NeoContentRenderer(),
        styles = { p -> alertColors(p, variant).let { (bg, fg) -> flat(fg, bg) } },
    )

    private fun dot(variant: Variant) = ThemeEntry(
        componentRenderer = NeoContentRenderer(),
        styles = { p -> flat(p.emphasisColors(variant).first, transparent) },
    )

    private fun key(role: ComponentRole, variant: Variant = Variant.DEFAULT, part: Part = Part.NONE) = ThemeKey(role, variant, part)

    private val entries: Map<ThemeKey, ThemeEntry> = mapOf(
        key(ROOT) to ThemeEntry(styles = surface),
        key(MODAL) to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL), styles = surface),
        key(PANEL) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), styles = surface),
        key(HBOX) to ThemeEntry(styles = surface),
        key(VBOX) to ThemeEntry(styles = surface),
        key(BUTTON) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK, hoverShift = true), styles = interactive),
        key(BUTTON, SECONDARY) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), styles = interactive),
        key(BUTTON, PRIMARY) to ThemeEntry(
            decorationRenderers = listOf(NeoDoubleFrameDecoration(), margin(0, 1)),
            styles = interactive,
        ),
        key(BUTTON, LINK) to ThemeEntry(
            decorationRenderers = listOf(NeoUnderlineDecoration(), margin(0, 1)),
            styles = link,
        ),
        key(CHECK_BOX) to ThemeEntry(componentRenderer = NeoCheckBoxRenderer(), decorationRenderers = emptyList(), styles = interactiveOnSurface),
        key(RADIO_BUTTON) to ThemeEntry(styles = interactiveOnSurface),
        key(TOGGLE_BUTTON) to ThemeEntry(styles = interactiveOnSurface),
        key(HEADER) to ThemeEntry(styles = text),
        key(LABEL) to ThemeEntry(styles = text),
        key(PARAGRAPH) to ThemeEntry(styles = text),
        key(LIST_ITEM) to ThemeEntry(styles = text),
        key(ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), styles = text),
        key(INPUT) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), styles = field),
        key(NUMBER_INPUT) to ThemeEntry(styles = field),
        key(TEXT_AREA) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), styles = field),
        key(SLIDER) to ThemeEntry(styles = interactiveOnSurface),
        key(SCROLL_BAR) to ThemeEntry(styles = interactiveOnSurface),
        key(LOG_AREA) to ThemeEntry(styles = text),
        key(PROGRESS_BAR) to ThemeEntry(
            componentRenderer = NeoProgressBarRenderer(),
            decorationRenderers = framed(NeoShadow.THIN),
            styles = surface,
        ),
        key(ALERT, INFO) to alert(INFO),
        key(ALERT, SUCCESS) to alert(SUCCESS),
        key(ALERT, ERROR) to alert(ERROR),
        key(ALERT, WARNING) to alert(WARNING),
        key(ALERT, INFO, Part.TEXT) to alertText(INFO),
        key(ALERT, SUCCESS, Part.TEXT) to alertText(SUCCESS),
        key(ALERT, ERROR, Part.TEXT) to alertText(ERROR),
        key(ALERT, WARNING, Part.TEXT) to alertText(WARNING),
        key(ALERT, INFO, Part.ICON) to alertText(INFO),
        key(ALERT, SUCCESS, Part.ICON) to alertText(SUCCESS),
        key(ALERT, ERROR, Part.ICON) to alertText(ERROR),
        key(ALERT, WARNING, Part.ICON) to alertText(WARNING),
        key(BADGE) to ThemeEntry(decorationRenderers = framed(NeoShadow.THIN), styles = surface),
        key(BADGE, part = Part.TEXT) to ThemeEntry(styles = text),
        key(BADGE, SUCCESS, Part.DOT) to dot(SUCCESS),
        key(BADGE, ERROR, Part.DOT) to dot(ERROR),
        key(BADGE, part = Part.DISMISS) to ThemeEntry(decorationRenderers = emptyList(), styles = inverted),
        key(CARD) to ThemeEntry(
            decorationRenderers = listOf(NeoFrameDecoration(NeoShadow.THICK), margin(1, 1)),
            styles = surface,
        ),
        key(CARD, part = Part.META) to ThemeEntry(styles = surface),
        key(CARD, part = Part.ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), styles = text),
        key(CARD, part = Part.SUBTITLE) to ThemeEntry(styles = mutedText),
        key(CARD, part = Part.TITLE) to ThemeEntry(styles = text),
        key(CARD, part = Part.BODY) to ThemeEntry(styles = text),
        key(WINDOW) to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL, divider = true), styles = surface),
        key(WINDOW, part = Part.TITLE_BAR) to ThemeEntry(styles = { p -> flat(p.onSelection, p.selection) }),
        key(WINDOW, part = Part.TITLE) to ThemeEntry(styles = text),
        key(WINDOW, part = Part.CONTROLS) to ThemeEntry(styles = text),
        key(WINDOW, part = Part.HEADER) to ThemeEntry(styles = text),
        key(WINDOW, part = Part.BODY) to ThemeEntry(styles = text),
        key(CHECK_BOX_FIELD) to ThemeEntry(styles = surface),
        key(CHECK_BOX_FIELD, part = Part.DESCRIPTION) to ThemeEntry(styles = text),
        key(INPUT_FIELD) to ThemeEntry(styles = surface),
        key(INPUT_FIELD, part = Part.LABEL) to ThemeEntry(styles = text),
        key(INPUT_FIELD, part = Part.ROW) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), styles = surface),
        key(INPUT_FIELD, part = Part.INPUT) to ThemeEntry(decorationRenderers = emptyList(), styles = field),
        key(INPUT_FIELD, part = Part.ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), styles = inverted),
        key(INPUT_FIELD, part = Part.BUTTON) to ThemeEntry(decorationRenderers = emptyList(), styles = accentBar),
        key(PROGRESS_FIELD) to ThemeEntry(styles = surface),
        key(PROGRESS_FIELD, part = Part.HEADER) to ThemeEntry(styles = surface),
        key(PROGRESS_FIELD, part = Part.LABEL) to ThemeEntry(styles = text),
        key(PROGRESS_FIELD, part = Part.VALUE) to ThemeEntry(styles = text),
        key(TEXT_AREA_FIELD) to ThemeEntry(styles = surface),
        key(TEXT_AREA_FIELD, part = Part.LABEL) to ThemeEntry(styles = text),
        key(TEXT_AREA_FIELD, part = Part.ACTIONS) to ThemeEntry(styles = surface),
        key(TAB_BAR) to ThemeEntry(componentRenderer = NeoTabBarRenderer(), decorationRenderers = emptyList(), styles = surface),
        key(TAB_BAR, part = Part.CONTENT) to ThemeEntry(decorationRenderers = emptyList(), styles = surface),
        key(TAB_BAR, part = Part.SCROLL) to ThemeEntry(decorationRenderers = listOf(box(BoxType.HEAVY, renderingMode = INTERACTIVE)), styles = interactive),
        key(TAB) to ThemeEntry(componentRenderer = NeoTabRenderer(), decorationRenderers = emptyList(), styles = tab),
    )

    override fun entryFor(key: ThemeKey): ThemeEntry? = entries[key]

    override fun toString(): String = name

    /** How far an alert's wash blends toward the surface, and its text toward the surface's text. */
    private const val WASH = 0.8
    private const val INK = 0.6
}
