package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
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
import org.hexworks.zircon.api.uievent.*

class DefaultButton(componentMetadata: ComponentMetadata,
                    initialText: String,
                    private val renderingStrategy: ComponentRenderingStrategy<Button>)
    : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText) {

    override val isEnabled: Boolean
        get() = enabledValue.value

    // TODO: regression test this (does the same as enable/disable)
    override val enabledValue = createPropertyFrom(true).apply {
        onChange { (_, _, newValue) ->
            if (newValue) enable() else disable()
        }
    }

    init {
        render()
        textProperty.onChange {
            LOGGER.debug("Text property of Button (id=${id.abbreviate()}, enabled=$isEnabled)" +
                    " changed from '${it.oldValue}' to '${it.newValue}'.")
            render()
        }
    }

    override fun enable() {
        LOGGER.debug("Enabling Button (id=${id.abbreviate()}, enabled=$isEnabled).")
        enabledValue.value = true
        componentStyleSet.reset()
        render()
    }

    override fun disable() {
        LOGGER.debug("Disabling Button (id=${id.abbreviate()}, enabled=$isEnabled).")
        enabledValue.value = false
        componentStyleSet.applyDisabledStyle()
        render()
    }

    override fun acceptsFocus() = isEnabled

    override fun focusGiven(): UIEventResponse {
        LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
        return Processed
    }

    override fun focusTaken(): UIEventResponse {
        LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) lost focus.")
        componentStyleSet.reset()
        render()
        return Processed
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (isEnabled && phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (isEnabled && phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else {
            LOGGER.debug("Mouse exited disabled Button (id=${id.abbreviate()}). Event ignored.")
            Pass
        }
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (isEnabled && phase == UIEventPhase.TARGET) {
            LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else {
            LOGGER.debug("Mouse pressed disabled Button (id=${id.abbreviate()}). Event ignored.")
            Pass
        }
    }

    override fun activated(): UIEventResponse {
        return if (isEnabled) {
            LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was activated.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else {
            LOGGER.warn("Trying to activate disabled Button (id=${id.abbreviate()}). Request dropped.")
            Pass
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme: $colorTheme to Button (id=${id.abbreviate()}, enabled=$isEnabled).")
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
        LOGGER.debug("Button (id=${id.abbreviate()}, enabled=$isEnabled) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Button::class)
    }
}
