package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.component.theme.Variant.*
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ThemeKey
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentRole.*
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.NeobrutalismPalette
import org.hexworks.zircon.api.component.theme.ThemeEntry
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.graphics.BoxType

/**
 * Hands the decorations the palette of the theme a component is drawn with, so a decoration
 * built for the light theme keeps working when the component moves to the dark one.
 */
public fun interface NeobrutalismPaletteProvider {
    public fun paletteFor(component: Component): NeobrutalismPalette?
}

/**
 * The Neobrutalism theme: see `docs/neo-brutalism.md` for the glyph mapping.
 */
public class NeobrutalismTheme(
    override val mode: ThemeMode,
    public val palette: NeobrutalismPalette,
) : ComponentTheme {

    override val name: String = "Neobrutalism (${mode.name.lowercase()})"

    private val palettes = NeobrutalismPaletteProvider { component ->
        (component.componentTheme as? NeobrutalismTheme)?.palette ?: palette
    }

    // Decorations, outermost first (the order DefaultComponentRenderingStrategy renders them).
    private fun framed(shadow: NeoShadow, hoverShift: Boolean = false, divider: Boolean = false, pad: Int = 1): List<ComponentDecorationRenderer> =
        listOf(NeoFrameDecoration(palettes, shadow, BoxType.HEAVY, hoverShift, divider)) + if (pad > 0) listOf(margin(0, pad)) else emptyList()

    private val transparent = Color.TRANSPARENT

    private fun flat(foreground: Color, background: Color): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = foreground; backgroundColor = background }
    }

    private val surface = flat(palette.ink, palette.paper)
    private val text = flat(palette.ink, transparent)
    private val mutedText = flat(palette.muted, transparent)
    private val accentBar = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accentStrong }
        highlightedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        focusedStyle = highlightedStyle
        activeStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = palette.paper }
    }
    private val interactive = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.paper }
        highlightedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        focusedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accentStrong }
        activeStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = palette.paper }
    }
    private val interactiveOnSurface = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = transparent }
        highlightedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        focusedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accentStrong }
        activeStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = transparent }
    }
    private val link = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.paper }
        highlightedStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        focusedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        activeStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = palette.paper }
    }
    private val inverted = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        highlightedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        focusedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accentStrong }
        activeStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accent }
        disabledStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.muted }
    }
    // The same for every interactive state: a field must not flicker on hover while focused.
    private val field = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.paper }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = palette.paper }
    }
    private val tab = componentStyleSet {
        defaultStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.paper }
        highlightedStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        focusedStyle = styleSet { foregroundColor = palette.ink; backgroundColor = palette.accentStrong }
        activeStyle = styleSet { foregroundColor = palette.paper; backgroundColor = palette.ink }
        disabledStyle = styleSet { foregroundColor = palette.muted; backgroundColor = palette.paper }
    }

    private fun alert(background: Color, foreground: Color) = ThemeEntry(
        decorationRenderers = framed(NeoShadow.THICK),
        componentStyleSet = flat(foreground, background),
    )

    private fun alertText(background: Color, foreground: Color) = ThemeEntry(
        componentRenderer = NeoContentRenderer(),
        componentStyleSet = flat(foreground, background),
    )

    private fun key(role: ComponentRole, variant: Variant = Variant.DEFAULT, part: Part = Part.NONE) = ThemeKey(role, variant, part)

    private val entries: Map<ThemeKey, ThemeEntry> = mapOf(
        key(ROOT) to ThemeEntry(componentStyleSet = surface),
        key(MODAL) to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL), componentStyleSet = surface),
        key(PANEL) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = surface),
        key(HBOX) to ThemeEntry(componentStyleSet = surface),
        key(VBOX) to ThemeEntry(componentStyleSet = surface),
        key(BUTTON) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK, hoverShift = true), componentStyleSet = interactive),
        key(BUTTON, SECONDARY) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = interactive),
        key(BUTTON, PRIMARY) to ThemeEntry(
            decorationRenderers = listOf(NeoDoubleFrameDecoration(palettes), margin(0, 1)),
            componentStyleSet = interactive,
        ),
        key(BUTTON, LINK) to ThemeEntry(
            decorationRenderers = listOf(NeoUnderlineDecoration(palettes), margin(0, 1)),
            componentStyleSet = link,
        ),
        key(CHECK_BOX) to ThemeEntry(componentRenderer = NeoCheckBoxRenderer(), decorationRenderers = emptyList(), componentStyleSet = interactiveOnSurface),
        key(RADIO_BUTTON) to ThemeEntry(componentStyleSet = interactiveOnSurface),
        key(TOGGLE_BUTTON) to ThemeEntry(componentStyleSet = interactiveOnSurface),
        key(HEADER) to ThemeEntry(componentStyleSet = text),
        key(LABEL) to ThemeEntry(componentStyleSet = text),
        key(PARAGRAPH) to ThemeEntry(componentStyleSet = text),
        key(LIST_ITEM) to ThemeEntry(componentStyleSet = text),
        key(ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = text),
        key(INPUT) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = field),
        key(NUMBER_INPUT) to ThemeEntry(componentStyleSet = field),
        key(TEXT_AREA) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = field),
        key(SLIDER) to ThemeEntry(componentStyleSet = interactiveOnSurface),
        key(SCROLL_BAR) to ThemeEntry(componentStyleSet = interactiveOnSurface),
        key(LOG_AREA) to ThemeEntry(componentStyleSet = text),
        key(PROGRESS_BAR) to ThemeEntry(
            componentRenderer = NeoProgressBarRenderer(),
            decorationRenderers = framed(NeoShadow.THIN),
            componentStyleSet = flat(palette.ink, palette.paper),
        ),
        key(ALERT, INFO) to alert(palette.infoBackground, palette.infoText),
        key(ALERT, SUCCESS) to alert(palette.successBackground, palette.successText),
        key(ALERT, ERROR) to alert(palette.errorBackground, palette.errorText),
        key(ALERT, INFO, Part.TEXT) to alertText(palette.infoBackground, palette.infoText),
        key(ALERT, SUCCESS, Part.TEXT) to alertText(palette.successBackground, palette.successText),
        key(ALERT, ERROR, Part.TEXT) to alertText(palette.errorBackground, palette.errorText),
        key(ALERT, INFO, Part.ICON) to alertText(palette.infoBackground, palette.infoText),
        key(ALERT, SUCCESS, Part.ICON) to alertText(palette.successBackground, palette.successText),
        key(ALERT, ERROR, Part.ICON) to alertText(palette.errorBackground, palette.errorText),
        key(BADGE) to ThemeEntry(decorationRenderers = framed(NeoShadow.THIN), componentStyleSet = surface),
        key(BADGE, part = Part.TEXT) to ThemeEntry(componentStyleSet = text),
        key(BADGE, SUCCESS, Part.DOT) to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = flat(palette.successDot, transparent)),
        key(BADGE, ERROR, Part.DOT) to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = flat(palette.errorDot, transparent)),
        key(BADGE, part = Part.DISMISS) to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = inverted),
        key(CARD) to ThemeEntry(
            decorationRenderers = listOf(NeoFrameDecoration(palettes, NeoShadow.THICK), margin(1, 1)),
            componentStyleSet = surface,
        ),
        key(CARD, part = Part.META) to ThemeEntry(componentStyleSet = surface),
        key(CARD, part = Part.ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = text),
        key(CARD, part = Part.SUBTITLE) to ThemeEntry(componentStyleSet = mutedText),
        key(CARD, part = Part.TITLE) to ThemeEntry(componentStyleSet = text),
        key(CARD, part = Part.BODY) to ThemeEntry(componentStyleSet = text),
        key(WINDOW) to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL, divider = true), componentStyleSet = surface),
        key(WINDOW, part = Part.TITLE_BAR) to ThemeEntry(componentStyleSet = flat(palette.ink, palette.accent)),
        key(WINDOW, part = Part.TITLE) to ThemeEntry(componentStyleSet = text),
        key(WINDOW, part = Part.CONTROLS) to ThemeEntry(componentStyleSet = text),
        key(WINDOW, part = Part.HEADER) to ThemeEntry(componentStyleSet = text),
        key(WINDOW, part = Part.BODY) to ThemeEntry(componentStyleSet = text),
        key(CHECK_BOX_FIELD) to ThemeEntry(componentStyleSet = surface),
        key(CHECK_BOX_FIELD, part = Part.DESCRIPTION) to ThemeEntry(componentStyleSet = text),
        key(INPUT_FIELD) to ThemeEntry(componentStyleSet = surface),
        key(INPUT_FIELD, part = Part.LABEL) to ThemeEntry(componentStyleSet = text),
        key(INPUT_FIELD, part = Part.ROW) to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = surface),
        key(INPUT_FIELD, part = Part.INPUT) to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = field),
        key(INPUT_FIELD, part = Part.ICON) to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = inverted),
        key(INPUT_FIELD, part = Part.BUTTON) to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = accentBar),
        key(PROGRESS_FIELD) to ThemeEntry(componentStyleSet = surface),
        key(PROGRESS_FIELD, part = Part.HEADER) to ThemeEntry(componentStyleSet = surface),
        key(PROGRESS_FIELD, part = Part.LABEL) to ThemeEntry(componentStyleSet = text),
        key(PROGRESS_FIELD, part = Part.VALUE) to ThemeEntry(componentStyleSet = text),
        key(TEXT_AREA_FIELD) to ThemeEntry(componentStyleSet = surface),
        key(TEXT_AREA_FIELD, part = Part.LABEL) to ThemeEntry(componentStyleSet = text),
        key(TEXT_AREA_FIELD, part = Part.ACTIONS) to ThemeEntry(componentStyleSet = surface),
        key(TAB_BAR) to ThemeEntry(componentRenderer = NeoTabBarRenderer(), decorationRenderers = emptyList(), componentStyleSet = surface),
        key(TAB_BAR, part = Part.CONTENT) to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = surface),
        key(TAB_BAR, part = Part.SCROLL) to ThemeEntry(decorationRenderers = listOf(box(BoxType.HEAVY, renderingMode = INTERACTIVE)), componentStyleSet = interactive),
        key(TAB) to ThemeEntry(componentRenderer = NeoTabRenderer(palettes), decorationRenderers = emptyList(), componentStyleSet = tab),
    )

    override fun entryFor(key: ThemeKey): ThemeEntry? = entries[key]

    override fun toString(): String = name
}
