package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*

class DefaultCheckBox(componentMetadata: ComponentMetadata,
                      initialText: String,
                      private val renderingStrategy: ComponentRenderingStrategy<CheckBox>)
    : CheckBox, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText),
        Selectable by Selectable.create() {

    override val state: CheckBoxState
        get() = checkBoxState

    private var checkBoxState = UNCHECKED
    private var pressing = false

    init {
        render()
        textProperty.onChange {
            LOGGER.debug("Text property of CheckBox (id=${id.abbreviate()}, selected=$isSelected)" +
                    " changed from '${it.oldValue}' to '${it.newValue}'.")
            render()
        }
        selectedProperty.onChange {
            render()
        }
    }

    // TODO: test this rudimentary state machine
    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was mouse exited.")
            pressing = false
            checkBoxState = if (isSelected) CHECKED else UNCHECKED
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was mouse pressed.")
            pressing = true
            checkBoxState = if (isSelected) UNCHECKING else CHECKING
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun activated(): UIEventResponse {
        LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was activated.")
        componentStyleSet.applyMouseOverStyle()
        pressing = false
        isSelected = isSelected.not()
        checkBoxState = if (isSelected) CHECKED else UNCHECKED
        render()
        return Processed
    }

    override fun acceptsFocus() = true

    override fun focusGiven(): UIEventResponse {
        LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
        return Processed
    }

    override fun focusTaken(): UIEventResponse {
        LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) lost focus.")
        componentStyleSet.reset()
        render()
        return Processed
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) for CheckBox (id=${id.abbreviate()}, selected=$isSelected).")
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
        LOGGER.debug("CheckBox (id=${id.abbreviate()}, selected=$isSelected) was rendered.")
        renderingStrategy.render(this, graphics)
    }

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
