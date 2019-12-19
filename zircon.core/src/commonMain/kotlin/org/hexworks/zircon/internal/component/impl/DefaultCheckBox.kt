package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*
import kotlin.jvm.Synchronized

@Suppress("DuplicatedCode")
class DefaultCheckBox(componentMetadata: ComponentMetadata,
                      initialText: String,
                      private val renderingStrategy: ComponentRenderingStrategy<CheckBox>)
    : CheckBox, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Selectable by Selectable.create(),
        TextHolder by TextHolder.create(initialText) {

    override var checkBoxState = UNCHECKED
        private set

    private var pressing = false

    init {
        render()
        textProperty.onChange {
            LOGGER.debug("Text property of CheckBox (id=${id.abbreviate()}, selected=$isSelected)" +
                    " changed from '${it.oldValue}' to '${it.newValue}'.")
            render()
        }
        selectedProperty.onChange {
            this.checkBoxState = if (it.newValue) CHECKED else UNCHECKED
            render()
        }
        disabledProperty.onChange {
            if (it.newValue) {
                LOGGER.debug("Disabling CheckBox (id=${id.abbreviate()}, disabled=$isDisabled, text=$text).")
                componentStyleSet.applyDisabledStyle()
            } else {
                LOGGER.debug("Enabling CheckBox (id=${id.abbreviate()}, disabled=$isDisabled, text=$text).")
                componentStyleSet.reset()
            }
            render()
        }
    }

    // TODO: test this rudimentary state machine

    @Synchronized
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was mouse exited.")
            pressing = false
            this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was mouse pressed.")
            pressing = true
            this.checkBoxState = if (isSelected) UNCHECKING else CHECKING
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun activated(): UIEventResponse {
        return if (isDisabled.not()) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was activated.")
            pressing = false
            isSelected = isSelected.not()
            this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun focusTaken() = whenEnabled {
        LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) lost focus.")
        pressing = false
        componentStyleSet.reset()
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
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .build()

    enum class CheckBoxState {
        CHECKING,
        CHECKED,
        UNCHECKING,
        UNCHECKED
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(CheckBox::class)
    }
}
