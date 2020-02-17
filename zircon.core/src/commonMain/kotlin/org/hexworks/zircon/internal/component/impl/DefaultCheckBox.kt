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
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
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
    }

    // TODO: test this rudimentary state machine

    @Synchronized
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            pressing = false
            this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        }
        super.mouseExited(event, phase)
    }

    @Synchronized
    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            pressing = true
            this.checkBoxState = if (isSelected) UNCHECKING else CHECKING
        }
        super.mousePressed(event, phase)
    }

    @Synchronized
    override fun activated() = whenEnabledRespondWith {
        pressing = false
        isSelected = isSelected.not()
        this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        super.activated()
    }

    @Synchronized
    override fun focusTaken() = whenEnabled {
        pressing = false
        super.focusTaken()
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
