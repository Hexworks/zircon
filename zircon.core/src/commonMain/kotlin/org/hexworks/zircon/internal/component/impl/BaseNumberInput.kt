package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.LEFT
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.RIGHT
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.BACKSPACE
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.DEL
import org.hexworks.zircon.internal.component.impl.textedit.transformation.InsertCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.MoveCursor
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.util.orElse

//TODO: Finish minValue impl. and bug fixing
abstract class BaseNumberInput(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int,
    componentMetadata: ComponentMetadata,
    protected val renderingStrategy: ComponentRenderingStrategy<NumberInput>
) : NumberInput, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    final override var text: String
        get() = _textBuffer.getText()
        set(value) {
            if (value.length <= maxNumberLength) {
                val clean = value.replace(Regex("[^\\d]"), "")
                _textBuffer = when {
                    clean == "" -> EditableTextBuffer.create("")
                    clean.toInt() <= maxValue -> EditableTextBuffer.create(clean, _textBuffer.cursor)
                    else -> _textBuffer
                }
            }
        }

    protected var _textBuffer = EditableTextBuffer.create("$initialValue")
    abstract var maxNumberLength: Int
    private var textBeforeModifications = ""

    final override val currentValueProperty = initialValue.toProperty()
    final override var currentValue: Int by currentValueProperty.asDelegate()

    init {
        currentValueProperty.onChange {
            if (it.newValue in minValue..maxValue) {
                text = it.newValue.toString()
            }
        }

        currentValue = when {
            initialValue < minValue -> minValue
            initialValue > maxValue -> maxValue
            else -> initialValue
        }
    }

    override fun incrementCurrentValue() {
        if (currentValue < maxValue) {
            currentValue++
        }
    }

    override fun decrementCurrentValue() {
        if (currentValue > minValue) {
            currentValue--
        }
    }

    override fun textBuffer() = _textBuffer

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
        .withDefaultStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.secondaryBackgroundColor)
                .withBackgroundColor(colorTheme.secondaryForegroundColor)
                .build()
        )
        .withDisabledStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.secondaryForegroundColor)
                .withBackgroundColor(TileColor.transparent())
                .build()
        )
        .withFocusedStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.primaryBackgroundColor)
                .withBackgroundColor(colorTheme.primaryForegroundColor)
                .build()
        )
        .build()

    override fun focusGiven() = whenEnabled {
        textBeforeModifications = text
        text = ""
        refreshCursor()
        componentState = ComponentState.FOCUSED
    }

    override fun focusTaken() = whenEnabled {
        text = textBeforeModifications
        computeNumberValue()
        componentState = ComponentState.DEFAULT
        Zircon.eventBus.publish(
            event = ZirconEvent.HideCursor(this),
            eventScope = ZirconScope
        )
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            if (isNavigationKey(event)) {
                Pass
            } else {
                when (event.code) {
                    KeyCode.ENTER -> {
                        saveModifications()
                        clearFocus()
                    }
                    KeyCode.ESCAPE -> clearFocus()
                    KeyCode.RIGHT -> _textBuffer.applyTransformation(MoveCursor(RIGHT))
                    KeyCode.LEFT -> _textBuffer.applyTransformation(MoveCursor(LEFT))
                    KeyCode.DELETE -> _textBuffer.applyTransformation(DeleteCharacter(DEL))
                    KeyCode.BACKSPACE -> _textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
                    else -> {
                        event.key.forEach { char ->
                            if (TextUtils.isDigitCharacter(char)) {
                                checkAndAddChar(char)
                            }
                        }
                    }
                }
                refreshCursor()
                Processed
            }
        } else Pass
    }

    private fun isNavigationKey(event: KeyboardEvent) =
        event == TAB || event == REVERSE_TAB

    private fun checkAndAddChar(char: Char) {
        val virtualTextBuffer = EditableTextBuffer.create(text, _textBuffer.cursor)
        virtualTextBuffer.applyTransformation(InsertCharacter(char))
        if (virtualTextBuffer.getText().toInt() <= maxValue) {
            if (text.length == maxNumberLength) {
                _textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
            }
            _textBuffer.applyTransformation(InsertCharacter(char))
        } else {
            _textBuffer.getCharAtOrNull(_textBuffer.cursor.position)?.let {
                _textBuffer.applyTransformation(DeleteCharacter(DEL))
            }.orElse { _textBuffer.applyTransformation(DeleteCharacter(BACKSPACE)) }
            checkAndAddChar(char)
        }
    }

    private fun computeNumberValue() {
        currentValue = when {
            text == "" -> minValue
            text.toInt() < minValue -> minValue
            text.toInt() > maxValue -> maxValue
            else -> text.toInt()
        }
    }

    private fun saveModifications() {
        textBeforeModifications = text
    }

    override fun onChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription {
        return currentValueProperty.onChange(fn)
    }

    protected abstract fun refreshCursor()

    companion object {
        val TAB = KeyboardEvent(
            type = KeyboardEventType.KEY_RELEASED,
            key = "\t",
            code = KeyCode.TAB
        )

        val REVERSE_TAB = KeyboardEvent(
            type = KeyboardEventType.KEY_RELEASED,
            key = "\t",
            code = KeyCode.TAB,
            shiftDown = true
        )
    }
}
