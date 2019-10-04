package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase

class DefaultButton(componentMetadata: ComponentMetadata,
                    initialText: String,
                    private val renderingStrategy: ComponentRenderingStrategy<Button>)
    : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText),
        Disablable by Disablable.create() {


    init {
        render()
        textProperty.onChange {
            LOGGER.debug("Text property of Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text)" +
                    " changed from '${it.oldValue}' to '${it.newValue}'.")
            render()
        }
        disabledProperty.onChange {
            if (it.newValue) {
                DefaultCheckBox.LOGGER.debug("Disabling Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text).")
                componentStyleSet.applyDisabledStyle()
            } else {
                DefaultCheckBox.LOGGER.debug("Enabling Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text).")
                componentStyleSet.reset()
            }
            render()
        }
    }

    override fun acceptsFocus() = isDisabled.not()

    override fun focusGiven() = whenEnabled {
        LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
    }

    override fun focusTaken() = whenEnabled {
        LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) lost focus.")
        componentStyleSet.reset()
        render()
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun activated() = whenEnabledRespondWith {
        if (isDisabled) {
            LOGGER.warn("Trying to activate disabled Button (id=${id.abbreviate()}, text=$text). Request dropped.")
            Pass
        } else {
            LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text) was activated.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme: $colorTheme to Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text).")
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
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun render() {
        LOGGER.debug("Button (id=${id.abbreviate()}, disabled=$isDisabled, hidden=$isHidden, text=$text) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Button::class)
    }
}
