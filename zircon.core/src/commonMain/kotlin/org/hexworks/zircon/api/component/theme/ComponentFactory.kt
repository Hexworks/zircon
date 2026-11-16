package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.HorizontalNumberInputBuilder
import org.hexworks.zircon.api.builder.component.HorizontalSliderBuilder
import org.hexworks.zircon.api.builder.component.IconBuilder
import org.hexworks.zircon.api.builder.component.InputBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.ListItemBuilder
import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.builder.component.ProgressBarBuilder
import org.hexworks.zircon.api.builder.component.RadioButtonBuilder
import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.builder.component.ToggleButtonBuilder
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.builder.component.VerticalNumberInputBuilder
import org.hexworks.zircon.api.builder.component.VerticalSliderBuilder
import org.hexworks.zircon.api.builder.fragment.AlertBuilder
import org.hexworks.zircon.api.builder.fragment.BadgeBuilder
import org.hexworks.zircon.api.builder.fragment.CardBuilder
import org.hexworks.zircon.api.builder.fragment.CheckBoxFieldBuilder
import org.hexworks.zircon.api.builder.fragment.HorizontalTabBarBuilder
import org.hexworks.zircon.api.builder.fragment.InputFieldBuilder
import org.hexworks.zircon.api.builder.fragment.ProgressFieldBuilder
import org.hexworks.zircon.api.builder.fragment.TextAreaFieldBuilder
import org.hexworks.zircon.api.builder.fragment.VerticalTabBarBuilder
import org.hexworks.zircon.api.builder.fragment.WindowBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Input
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.fragment.Alert
import org.hexworks.zircon.api.fragment.Badge
import org.hexworks.zircon.api.fragment.Card
import org.hexworks.zircon.api.fragment.CheckBoxField
import org.hexworks.zircon.api.fragment.InputField
import org.hexworks.zircon.api.fragment.ProgressField
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.TextAreaField
import org.hexworks.zircon.api.fragment.Window
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder

/**
 * Builds components and fragments pre-configured for a [ComponentTheme]: each builder gets
 * the theme's renderer, decorations and colors for its role *before* your block runs, so the
 * size is computed for the theme's decorations and anything you set in the block wins.
 *
 * Get one from [org.hexworks.zircon.api.screen.Screen.componentFactory] (it follows the
 * screen's current theme) or from [ComponentTheme.componentFactory].
 */
public class ComponentFactory(
    private val themeProvider: () -> ComponentTheme,
) {

    /**
     * The theme in effect for the next builder.
     */
    public val theme: ComponentTheme
        get() = themeProvider()

    /**
     * Pre-configures any component builder from the theme's entry for [role] (the builder's
     * own role when omitted), for component kinds this factory has no function for. Call it
     * after setting what you set: the theme fills in what is not pinned.
     */
    public fun <T : Component, B : BaseComponentBuilder<T>> configure(
        builder: B,
        role: ComponentRole = builder.role,
        variant: Variant = builder.variant,
        part: Part = builder.part,
    ): B {
        builder.role = role
        builder.variant = variant
        builder.part = part
        builder.applyThemeEntry(theme)
        return builder
    }

    // The theme is preset so the block's children inherit it; its entry is applied at build,
    // when the block's role and pins are known.
    private inline fun <T : Component, B : BaseComponentBuilder<T>> build(builder: B, init: B.() -> Unit): T {
        builder.presetTheme(theme)
        return builder.apply(init).build()
    }

    private inline fun <F, B : ThemedFragmentBuilder<F>> buildFragment(builder: B, init: B.() -> Unit): F {
        builder.theme = theme
        return builder.apply(init).build()
    }

    // Components

    public fun button(init: ButtonBuilder.() -> Unit): Button = build(ButtonBuilder(), init)
    public fun checkBox(init: CheckBoxBuilder.() -> Unit): CheckBox = build(CheckBoxBuilder(), init)
    public fun radioButton(init: RadioButtonBuilder.() -> Unit): RadioButton =
        build(RadioButtonBuilder(), init)
    public fun toggleButton(init: ToggleButtonBuilder.() -> Unit): ToggleButton =
        build(ToggleButtonBuilder(), init)
    public fun header(init: HeaderBuilder.() -> Unit): Header = build(HeaderBuilder(), init)
    public fun label(init: LabelBuilder.() -> Unit): Label = build(LabelBuilder(), init)
    public fun paragraph(init: ParagraphBuilder.() -> Unit): Paragraph = build(ParagraphBuilder(), init)
    public fun listItem(init: ListItemBuilder.() -> Unit): ListItem = build(ListItemBuilder(), init)
    public fun icon(init: IconBuilder.() -> Unit): Icon = build(IconBuilder(), init)
    public fun panel(init: PanelBuilder.() -> Unit): Panel = build(PanelBuilder(), init)
    public fun hBox(init: HBoxBuilder.() -> Unit): HBox = build(HBoxBuilder(), init)
    public fun vBox(init: VBoxBuilder.() -> Unit): VBox = build(VBoxBuilder(), init)
    public fun input(init: InputBuilder.() -> Unit): Input = build(InputBuilder(), init)
    public fun textArea(init: TextAreaBuilder.() -> Unit): TextArea = build(TextAreaBuilder(), init)
    public fun progressBar(init: ProgressBarBuilder.() -> Unit): ProgressBar =
        build(ProgressBarBuilder(), init)
    public fun logArea(init: LogAreaBuilder.() -> Unit): LogArea = build(LogAreaBuilder(), init)
    public fun horizontalNumberInput(init: HorizontalNumberInputBuilder.() -> Unit): NumberInput =
        build(HorizontalNumberInputBuilder(), init)
    public fun verticalNumberInput(init: VerticalNumberInputBuilder.() -> Unit): NumberInput =
        build(VerticalNumberInputBuilder(), init)
    public fun horizontalSlider(init: HorizontalSliderBuilder.() -> Unit): Slider =
        build(HorizontalSliderBuilder(), init)
    public fun verticalSlider(init: VerticalSliderBuilder.() -> Unit): Slider =
        build(VerticalSliderBuilder(), init)

    // Fragments

    public fun alert(init: AlertBuilder.() -> Unit): Alert = buildFragment(AlertBuilder(), init)
    public fun badge(init: BadgeBuilder.() -> Unit): Badge = buildFragment(BadgeBuilder(), init)
    public fun card(init: CardBuilder.() -> Unit): Card = buildFragment(CardBuilder(), init)
    public fun window(init: WindowBuilder.() -> Unit): Window = buildFragment(WindowBuilder(), init)
    public fun checkBoxField(init: CheckBoxFieldBuilder.() -> Unit): CheckBoxField = buildFragment(CheckBoxFieldBuilder(), init)
    public fun inputField(init: InputFieldBuilder.() -> Unit): InputField = buildFragment(InputFieldBuilder(), init)
    public fun progressField(init: ProgressFieldBuilder.() -> Unit): ProgressField = buildFragment(ProgressFieldBuilder(), init)
    public fun textAreaField(init: TextAreaFieldBuilder.() -> Unit): TextAreaField = buildFragment(TextAreaFieldBuilder(), init)
    public fun horizontalTabBar(init: HorizontalTabBarBuilder.() -> Unit): TabBar = buildFragment(HorizontalTabBarBuilder(), init)
    public fun verticalTabBar(init: VerticalTabBarBuilder.() -> Unit): TabBar = buildFragment(VerticalTabBarBuilder(), init)
}
