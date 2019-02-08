package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.*

class DefaultToggleButton(componentMetadata: ComponentMetadata,
                          initialText: String,
                          initialSelected: Boolean,
                          private val renderingStrategy: ComponentRenderingStrategy<ToggleButton>)
    : ToggleButton, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText),
        Selectable by Selectable.create(initialSelected) {

    init {
        // TODO: when the toggle button is selected by default (on create) it should be rendered as selected
        // TODO: this if branch is supposed to do the trick, but it doesn't for some reason.
        if (isSelected) {
            applyIsSelectedStyle()
        }
        render()
        textProperty.onChange {
            render()
        }
        selectedProperty.onChange {
            if (it.newValue) {
                applyIsSelectedStyle()
            } else {
                componentStyleSet.reset()
            }
            render()
        }
    }

    override fun activated(): UIEventResponse {
        selectedProperty.value = !isSelected
        return Processed
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (isSelected.not() && phase == UIEventPhase.TARGET) {
            super.mouseExited(event, phase)
            Processed
        } else Pass
    }


    override fun acceptsFocus() = true

    override fun focusGiven(): UIEventResponse {
        componentStyleSet.applyFocusedStyle()
        render()
        return Processed
    }

    override fun focusTaken(): UIEventResponse {
        if (isSelected) {
            applyIsSelectedStyle()
        } else {
            componentStyleSet.reset()
        }
        render()
        return Processed
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

    private fun applyIsSelectedStyle() {
        componentStyleSet.applyMouseOverStyle()
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(ToggleButton::class)
    }
}
