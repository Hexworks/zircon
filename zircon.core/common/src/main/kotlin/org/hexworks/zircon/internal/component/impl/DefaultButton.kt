package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.internal.behavior.DefaultTextHolder

class DefaultButton(componentMetadata: ComponentMetadata,
                    initialText: String,
                    private val renderingStrategy: ComponentRenderingStrategy<Button>)
    : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy), TextHolder by DefaultTextHolder(initialText) {

    override val isEnabled: Boolean
        get() = enabledValue.value

    override val enabledValue = createPropertyFrom(true).apply {
        onChange { render() }
    }

    init {
        render()
        textProperty.onChange {
            render()
        }
    }

    override fun enable() {
        enabledValue.value = true
        componentStyleSet.reset()
        render()
    }

    override fun disable() {
        enabledValue.value = false
        componentStyleSet.applyDisabledStyle()
        render()
    }

    override fun acceptsFocus(): Boolean {
        return isEnabled
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        return if (isEnabled) {
            componentStyleSet.applyFocusedStyle()
            render()
            true
        } else false
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet.reset()
        render()
    }

    override fun mouseEntered(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyMouseOverStyle()
            render()
        }
    }

    override fun mouseExited(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.reset()
            render()
        }
    }

    override fun mousePressed(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyActiveStyle()
            render()
        }
    }

    override fun mouseReleased(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyMouseOverStyle()
            render()
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.accentColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryBackgroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryBackgroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .withActiveStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }
}
