package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase

@Suppress("DuplicatedCode")
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
        // TODO: when the toggle button is selected by default (on create) it should be rendered as
        // TODO: selected this if branch is supposed to do the trick, but it doesn't for some reason.
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
        disabledProperty.onChange {
            if (it.newValue) {
                componentStyleSet.applyDisabledStyle()
            } else {
                componentStyleSet.reset()
            }
            render()
        }
    }

    override fun activated() = whenEnabled {
        selectedProperty.value = !isSelected
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabled {
        if (isSelected.not() && phase == UIEventPhase.TARGET) {
            super.mouseExited(event, phase)
        }
    }

    override fun focusTaken() = whenEnabled {
        if (isSelected) {
            applyIsSelectedStyle()
        } else {
            componentStyleSet.reset()
        }
        render()
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
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
            .build()

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
