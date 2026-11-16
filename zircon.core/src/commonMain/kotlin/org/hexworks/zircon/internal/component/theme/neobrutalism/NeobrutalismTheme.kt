package org.hexworks.zircon.internal.component.theme.neobrutalism

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
    private val selectedTab = flat(palette.ink, palette.accent)

    private fun alert(background: Color, foreground: Color) = ThemeEntry(
        decorationRenderers = framed(NeoShadow.THICK),
        componentStyleSet = flat(foreground, background),
    )

    private fun alertText(background: Color, foreground: Color) = ThemeEntry(
        componentRenderer = NeoContentRenderer(),
        componentStyleSet = flat(foreground, background),
    )

    private val entries: Map<ComponentRole, ThemeEntry> = mapOf(
        ROOT to ThemeEntry(componentStyleSet = surface),
        MODAL to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL), componentStyleSet = surface),
        PANEL to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = surface),
        HBOX to ThemeEntry(componentStyleSet = surface),
        VBOX to ThemeEntry(componentStyleSet = surface),

        BUTTON to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK, hoverShift = true), componentStyleSet = interactive),
        BUTTON_SECONDARY to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = interactive),
        BUTTON_PRIMARY to ThemeEntry(
            decorationRenderers = listOf(NeoDoubleFrameDecoration(palettes), margin(0, 1)),
            componentStyleSet = interactive,
        ),
        BUTTON_LINK to ThemeEntry(
            decorationRenderers = listOf(NeoUnderlineDecoration(palettes), margin(0, 1)),
            componentStyleSet = link,
        ),

        CHECK_BOX to ThemeEntry(componentRenderer = NeoCheckBoxRenderer(), decorationRenderers = emptyList(), componentStyleSet = interactiveOnSurface),
        RADIO_BUTTON to ThemeEntry(componentStyleSet = interactiveOnSurface),
        TOGGLE_BUTTON to ThemeEntry(componentStyleSet = interactiveOnSurface),

        HEADER to ThemeEntry(componentStyleSet = text),
        LABEL to ThemeEntry(componentStyleSet = text),
        PARAGRAPH to ThemeEntry(componentStyleSet = text),
        LIST_ITEM to ThemeEntry(componentStyleSet = text),
        ICON to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = text),

        INPUT to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = field),
        NUMBER_INPUT to ThemeEntry(componentStyleSet = field),
        TEXT_AREA to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = field),
        SLIDER to ThemeEntry(componentStyleSet = interactiveOnSurface),
        SCROLL_BAR to ThemeEntry(componentStyleSet = interactiveOnSurface),
        LOG_AREA to ThemeEntry(componentStyleSet = text),
        PROGRESS_BAR to ThemeEntry(
            componentRenderer = NeoProgressBarRenderer(),
            decorationRenderers = framed(NeoShadow.THIN),
            componentStyleSet = flat(palette.ink, palette.paper),
        ),

        ALERT_INFO to alert(palette.infoBackground, palette.infoText),
        ALERT_SUCCESS to alert(palette.successBackground, palette.successText),
        ALERT_ERROR to alert(palette.errorBackground, palette.errorText),
        ALERT_INFO_TEXT to alertText(palette.infoBackground, palette.infoText),
        ALERT_SUCCESS_TEXT to alertText(palette.successBackground, palette.successText),
        ALERT_ERROR_TEXT to alertText(palette.errorBackground, palette.errorText),

        BADGE to ThemeEntry(decorationRenderers = framed(NeoShadow.THIN), componentStyleSet = surface),
        BADGE_TEXT to ThemeEntry(componentStyleSet = text),
        BADGE_DOT_SUCCESS to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = flat(palette.successDot, transparent)),
        BADGE_DOT_ERROR to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = flat(palette.errorDot, transparent)),
        BADGE_DISMISS to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = inverted),

        CARD to ThemeEntry(
            decorationRenderers = listOf(NeoFrameDecoration(palettes, NeoShadow.THICK), margin(1, 1)),
            componentStyleSet = surface,
        ),
        CARD_META to ThemeEntry(componentStyleSet = surface),
        CARD_ICON to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = text),
        CARD_SUBTITLE to ThemeEntry(componentStyleSet = mutedText),
        CARD_TITLE to ThemeEntry(componentStyleSet = text),
        CARD_BODY to ThemeEntry(componentStyleSet = text),

        WINDOW to ThemeEntry(decorationRenderers = framed(NeoShadow.FULL, divider = true), componentStyleSet = surface),
        WINDOW_TITLE_BAR to ThemeEntry(componentStyleSet = flat(palette.ink, palette.accent)),
        WINDOW_TITLE to ThemeEntry(componentStyleSet = text),
        WINDOW_CONTROLS to ThemeEntry(componentStyleSet = text),
        WINDOW_HEADER to ThemeEntry(componentStyleSet = text),
        WINDOW_BODY to ThemeEntry(componentStyleSet = text),

        CHECK_BOX_FIELD to ThemeEntry(componentStyleSet = surface),
        CHECK_BOX_DESCRIPTION to ThemeEntry(componentStyleSet = text),

        INPUT_FIELD to ThemeEntry(componentStyleSet = surface),
        INPUT_FIELD_LABEL to ThemeEntry(componentStyleSet = text),
        INPUT_FIELD_ROW to ThemeEntry(decorationRenderers = framed(NeoShadow.THICK), componentStyleSet = surface),
        INPUT_FIELD_INPUT to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = field),
        INPUT_FIELD_ICON to ThemeEntry(componentRenderer = NeoContentRenderer(), componentStyleSet = inverted),
        INPUT_FIELD_BUTTON to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = accentBar),

        PROGRESS_FIELD to ThemeEntry(componentStyleSet = surface),
        PROGRESS_FIELD_HEADER to ThemeEntry(componentStyleSet = surface),
        PROGRESS_FIELD_LABEL to ThemeEntry(componentStyleSet = text),
        PROGRESS_FIELD_VALUE to ThemeEntry(componentStyleSet = text),

        TEXT_AREA_FIELD to ThemeEntry(componentStyleSet = surface),
        TEXT_AREA_FIELD_LABEL to ThemeEntry(componentStyleSet = text),
        TEXT_AREA_FIELD_ACTIONS to ThemeEntry(componentStyleSet = surface),

        TAB_BAR to ThemeEntry(componentRenderer = NeoTabBarRenderer(vertical = false), decorationRenderers = emptyList(), componentStyleSet = surface),
        TAB_CONTENT to ThemeEntry(decorationRenderers = emptyList(), componentStyleSet = surface),
        TAB to ThemeEntry(componentRenderer = NeoTabRenderer(vertical = false), decorationRenderers = emptyList(), componentStyleSet = tab),
        TAB_SELECTED to ThemeEntry(componentRenderer = NeoSelectedTabRenderer(vertical = false), decorationRenderers = emptyList(), componentStyleSet = selectedTab),
        TAB_SCROLL to ThemeEntry(decorationRenderers = listOf(box(BoxType.HEAVY, renderingMode = INTERACTIVE)), componentStyleSet = interactive),
        TAB_BAR_VERTICAL to ThemeEntry(componentRenderer = NeoTabBarRenderer(vertical = true), decorationRenderers = emptyList(), componentStyleSet = surface),
        TAB_VERTICAL to ThemeEntry(componentRenderer = NeoTabRenderer(vertical = true), decorationRenderers = emptyList(), componentStyleSet = tab),
        TAB_VERTICAL_SELECTED to ThemeEntry(componentRenderer = NeoSelectedTabRenderer(vertical = true), decorationRenderers = emptyList(), componentStyleSet = selectedTab),
    )

    override fun entryFor(role: ComponentRole): ThemeEntry? = entries[role]

    override fun toString(): String = name
}
