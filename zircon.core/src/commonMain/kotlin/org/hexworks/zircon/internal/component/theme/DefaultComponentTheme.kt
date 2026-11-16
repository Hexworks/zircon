package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.theme.ComponentRole.*
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ThemeEntry
import org.hexworks.zircon.api.component.theme.ThemeKey
import org.hexworks.zircon.api.component.theme.ThemeMode
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
 * The stock look: the renderer and decorations every component kind is built with when no
 * theme says otherwise, and the look every other theme falls back to. It has an entry for
 * every kind (so a theme need not repeat a renderer it does not change) and the tab bar
 * parts; it has no colors, those come from the color theme until the palette lands.
 *
 * Decorations are in rendering order, outermost first.
 */
internal object DefaultComponentTheme : ComponentTheme {

    override val name: String = "Default"

    override val mode: ThemeMode = ThemeMode.DARK

    private fun entry(
        renderer: ComponentRenderer<*>,
        decorations: List<ComponentDecorationRenderer> = emptyList(),
    ) = ThemeEntry(componentRenderer = renderer, decorationRenderers = decorations)

    private val entries: Map<ThemeKey, ThemeEntry> = mapOf(
        ThemeKey(BUTTON) to entry(DefaultButtonRenderer(), listOf(side())),
        ThemeKey(CHECK_BOX) to entry(DefaultCheckBoxRenderer()),
        ThemeKey(RADIO_BUTTON) to entry(DefaultRadioButtonRenderer()),
        ThemeKey(TOGGLE_BUTTON) to entry(DefaultToggleButtonRenderer()),
        ThemeKey(HEADER) to entry(DefaultHeaderRenderer()),
        ThemeKey(LABEL) to entry(DefaultLabelRenderer()),
        ThemeKey(PARAGRAPH) to entry(DefaultParagraphRenderer()),
        ThemeKey(LIST_ITEM) to entry(DefaultListItemRenderer()),
        ThemeKey(ICON) to entry(DefaultIconRenderer()),
        ThemeKey(PANEL) to entry(DefaultPanelRenderer()),
        ThemeKey(HBOX) to entry(DefaultHBoxRenderer()),
        ThemeKey(VBOX) to entry(DefaultVBoxRenderer()),
        ThemeKey(MODAL) to entry(DefaultModalRenderer<Nothing>()),
        ThemeKey(INPUT) to entry(DefaultInputRenderer()),
        ThemeKey(NUMBER_INPUT) to entry(OrientedComponentRenderer<NumberInput>(DefaultNumberInputRenderer(), DefaultVerticalNumberInputRenderer())),
        ThemeKey(TEXT_AREA) to entry(DefaultTextAreaRenderer()),
        ThemeKey(SLIDER) to entry(OrientedComponentRenderer<Slider>(HorizontalSliderRenderer(), VerticalSliderRenderer())),
        ThemeKey(SCROLL_BAR) to entry(DefaultVerticalScrollBarRenderer()),
        ThemeKey(PROGRESS_BAR) to entry(DefaultProgressBarRenderer()),
        ThemeKey(LOG_AREA) to entry(DefaultLogAreaRenderer()),
        ThemeKey(TAB_BAR) to entry(NoOpComponentRenderer<Component>()),
        ThemeKey(TAB_BAR, part = Part.SCROLL) to ThemeEntry(decorationRenderers = listOf(box())),
        ThemeKey(TAB) to entry(DefaultTabRenderer()),
    )

    override fun entryFor(key: ThemeKey): ThemeEntry? = entries[key]

    override fun toString(): String = name
}
