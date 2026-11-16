package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.theme.ComponentRole.*
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.PaletteKey
import org.hexworks.zircon.api.component.theme.PaletteStyles
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.onBackground
import org.hexworks.zircon.api.component.theme.onPrimary
import org.hexworks.zircon.api.component.theme.primary
import org.hexworks.zircon.api.component.theme.surface
import org.hexworks.zircon.api.component.theme.textDisabled
import org.hexworks.zircon.api.component.theme.textMuted
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ThemeEntry
import org.hexworks.zircon.api.component.theme.ThemeKey
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultInputRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultModalRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultNumberInputRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultTabRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultVBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalNumberInputRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalScrollBarRenderer
import org.hexworks.zircon.internal.component.renderer.HorizontalSliderRenderer
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.component.renderer.OrientedComponentRenderer
import org.hexworks.zircon.internal.component.renderer.VerticalSliderRenderer

/**
 * The stock look: the renderer, decorations and color derivation every component kind is
 * built with when no theme says otherwise, and the look every other theme falls back to. It
 * has an entry for every kind (so a theme need not repeat a renderer it does not change) and
 * the tab bar parts. The derivations are the ones 1.x computed from its `ColorTheme`, in
 * palette terms: containers are the border color on the surface, labels are muted text,
 * buttons are the primary color at rest and the primary surface when hovered, fields are
 * sunken wells.
 *
 * Decorations are in rendering order, outermost first.
 */
internal object DefaultComponentTheme : ComponentTheme {

    override val name: String = "Default"

    private fun entry(
        renderer: ComponentRenderer<*>,
        styles: (ComponentPalette) -> ComponentStyleSet,
        decorations: List<ComponentDecorationRenderer> = emptyList(),
    ) = ThemeEntry(componentRenderer = renderer, decorationRenderers = decorations, styles = styles)

    /** The slider: body text at rest, focused and pressed alike so the handle does not flicker. */
    private val slider: (ComponentPalette) -> ComponentStyleSet = { palette ->
        val held = styleSet { foregroundColor = palette.onPrimary; backgroundColor = palette.primary }
        componentStyleSet {
            defaultStyle = styleSet { foregroundColor = palette.onBackground; backgroundColor = Color.TRANSPARENT }
            highlightedStyle = styleSet { foregroundColor = palette.textMuted; backgroundColor = palette.primary }
            focusedStyle = held
            activeStyle = held
            disabledStyle = styleSet { foregroundColor = palette.textDisabled; backgroundColor = palette.surface }
        }
    }

    /** The empty style set: an icon carries its own tile colors. */
    private val own: (ComponentPalette) -> ComponentStyleSet = { ComponentStyleSet.EMPTY }

    private val entries: Map<ThemeKey, ThemeEntry> = mapOf(
        ThemeKey(ROOT) to ThemeEntry(styles = PaletteStyles.background),
        ThemeKey(BUTTON) to entry(DefaultButtonRenderer(), PaletteStyles.interactive(), listOf(side())),
        ThemeKey(CHECK_BOX) to entry(DefaultCheckBoxRenderer(), PaletteStyles.interactive()),
        ThemeKey(RADIO_BUTTON) to entry(DefaultRadioButtonRenderer(), PaletteStyles.interactive()),
        ThemeKey(TOGGLE_BUTTON) to entry(DefaultToggleButtonRenderer(), PaletteStyles.interactive()),
        ThemeKey(HEADER) to entry(DefaultHeaderRenderer(), PaletteStyles.text(PaletteKey.ON_SURFACE)),
        ThemeKey(LABEL) to entry(DefaultLabelRenderer(), PaletteStyles.text(PaletteKey.TEXT_MUTED)),
        ThemeKey(PARAGRAPH) to entry(DefaultParagraphRenderer(), PaletteStyles.text(PaletteKey.TEXT_MUTED)),
        ThemeKey(LIST_ITEM) to entry(DefaultListItemRenderer(), PaletteStyles.text(PaletteKey.TEXT_MUTED)),
        ThemeKey(ICON) to entry(DefaultIconRenderer(), own),
        ThemeKey(PANEL) to entry(DefaultPanelRenderer(), PaletteStyles.surface),
        ThemeKey(HBOX) to entry(DefaultHBoxRenderer(), PaletteStyles.surface),
        ThemeKey(VBOX) to entry(DefaultVBoxRenderer(), PaletteStyles.surface),
        ThemeKey(MODAL) to entry(DefaultModalRenderer<Nothing>(), PaletteStyles.raised),
        ThemeKey(INPUT) to entry(DefaultInputRenderer(), PaletteStyles.field),
        ThemeKey(NUMBER_INPUT) to entry(OrientedComponentRenderer<NumberInput>(DefaultNumberInputRenderer(), DefaultVerticalNumberInputRenderer()), PaletteStyles.field),
        ThemeKey(TEXT_AREA) to entry(DefaultTextAreaRenderer(), PaletteStyles.field),
        ThemeKey(SLIDER) to entry(OrientedComponentRenderer<Slider>(HorizontalSliderRenderer(), VerticalSliderRenderer()), slider),
        ThemeKey(SCROLL_BAR) to entry(DefaultVerticalScrollBarRenderer(), PaletteStyles.interactive()),
        ThemeKey(PROGRESS_BAR) to entry(DefaultProgressBarRenderer(), PaletteStyles.text(PaletteKey.PRIMARY)),
        ThemeKey(LOG_AREA) to entry(DefaultLogAreaRenderer(), PaletteStyles.surface),
        ThemeKey(TAB_BAR) to entry(NoOpComponentRenderer<Component>(), PaletteStyles.surface),
        ThemeKey(TAB_BAR, part = Part.SCROLL) to ThemeEntry(decorationRenderers = listOf(box())),
        ThemeKey(TAB) to entry(DefaultTabRenderer(), PaletteStyles.interactive()),
    )

    // Every variant of an interactive kind gets its own emphasis colors.
    private val variants: Map<ThemeKey, ThemeEntry> = Variant.entries
        .filter { it != Variant.DEFAULT }
        .flatMap { variant ->
            listOf(BUTTON, CHECK_BOX, RADIO_BUTTON, TOGGLE_BUTTON).map { kind ->
                ThemeKey(kind, variant) to ThemeEntry(styles = PaletteStyles.interactive(variant))
            }
        }.toMap()


    override fun entryFor(key: ThemeKey): ThemeEntry? = entries[key] ?: variants[key]

    override fun toString(): String = name
}
