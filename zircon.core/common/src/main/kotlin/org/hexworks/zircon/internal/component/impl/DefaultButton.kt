package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.input.Input

class DefaultButton(componentMetadata: ComponentMetadata,
                    initialText: String,
                    private val renderingStrategy: ComponentRenderingStrategy<Button>)
    : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val textProperty = createPropertyFrom(initialText).also {
        it.onChange {
            render()
        }
    }

    override val text: String
        get() = textProperty.value

    init {
        render()
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        componentStyleSet.applyFocusedStyle()
        render()
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet.reset()
        render()
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
