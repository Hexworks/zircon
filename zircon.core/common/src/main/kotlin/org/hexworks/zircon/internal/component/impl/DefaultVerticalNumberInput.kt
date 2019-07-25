package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.RIGHT
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.LEFT
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.BACKSPACE
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.DEL
import org.hexworks.zircon.internal.component.impl.textedit.transformation.InsertCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.MoveCursor
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.math.min

class DefaultVerticalNumberInput constructor(
        initialValue: Int,
        val maxValue: Int,
        componentMetadata: ComponentMetadata,
        private val renderingStrategy: ComponentRenderingStrategy<NumberInput>)
    : NumberInput,
        Disablable by Disablable.create(),
        DefaultComponent(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    override var text: String
        get() = textBuffer.getText()
        set(value) {
            if (value.length <= maxNumberLength) {
                val clean = value.replace(Regex("[^\\d]"), "")
                if (clean.toInt() <= maxValue) {
                    textBuffer = EditableTextBuffer.create(clean, textBuffer.cursor)
                    computeDigitalValue()
                    render()
                }
            }
        }

    private var textBuffer = EditableTextBuffer.create("$initialValue")
    private var maxNumberLength = min(Int.MAX_VALUE.toString().length, size.height)
    override val currentValueProperty = createPropertyFrom(initialValue)
    override var currentValue: Int by currentValueProperty.asDelegate()

    init {
        this.text = "$initialValue"
        computeDigitalValue()
    }

    override fun textBuffer() = textBuffer

    override fun acceptsFocus() = isDisabled.not()

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryBackgroundColor)
                        .withBackgroundColor(colorTheme.secondaryForegroundColor)
                        .build())
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryBackgroundColor)
                        .withBackgroundColor(colorTheme.primaryForegroundColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun focusGiven() = whenEnabled {
        componentStyleSet.applyFocusedStyle()
        render()
        refreshCursor()
    }

    override fun focusTaken() = whenEnabled {
        componentStyleSet.reset()
        render()
        Zircon.eventBus.publish(
                event = ZirconEvent.HideCursor,
                eventScope = ZirconScope)
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            if (isNavigationKey(event)) {
                Pass
            } else {
                when (event.code) {
                    KeyCode.RIGHT -> textBuffer.applyTransformation(MoveCursor(RIGHT))
                    KeyCode.LEFT -> textBuffer.applyTransformation(MoveCursor(LEFT))
                    KeyCode.DELETE -> textBuffer.applyTransformation(DeleteCharacter(DEL))
                    KeyCode.BACKSPACE -> textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
                    else -> {
                        event.key.forEach { char ->
                            if (TextUtils.isDigitCharacter(char)) {
                                checkAndAddChar(char)
                            }
                        }
                    }
                }
                computeDigitalValue()
                refreshCursor()
                render()
                Processed
            }
        } else Pass
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }

    private fun isNavigationKey(event: KeyboardEvent) =
            event == TAB || event == REVERSE_TAB

    private fun refreshCursor() {
        var pos = textBuffer.cursor.position
        pos = pos.withX(min(pos.x, contentSize.height))
        pos = pos.withY(0)
        val invertedPosition = Positions.create(pos.y, pos.x)
        Zircon.eventBus.publish(
                event = ZirconEvent.RequestCursorAt(invertedPosition
                        .withRelative(absolutePosition + contentPosition)),
                eventScope = ZirconScope)
    }

    private fun checkAndAddChar(char: Char) {
        val virtualTextBuffer = EditableTextBuffer.create(text, textBuffer.cursor)
        virtualTextBuffer.applyTransformation(InsertCharacter(char))
        if (virtualTextBuffer.getText().toInt() <= maxValue) {
            if (text.length == maxNumberLength) {
                textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
            }
            textBuffer.applyTransformation(InsertCharacter(char))
        } else {
            if (textBuffer.getCharAt(textBuffer.cursor.position).isPresent) {
                textBuffer.applyTransformation(DeleteCharacter(DEL))
            } else {
                textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
            }
            checkAndAddChar(char)
        }
    }

    private fun computeDigitalValue() {
        currentValue = if (text == "") {
            0
        } else {
            text.toInt()
        }
    }

    override fun onChange(fn: ChangeListener<Int>): Subscription {
        return currentValueProperty.onChange(fn::onChange)
    }

    companion object {
        val TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_RELEASED,
                key = "\t",
                code = KeyCode.TAB)

        val REVERSE_TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_RELEASED,
                key = "\t",
                code = KeyCode.TAB,
                shiftDown = true)

        val LOGGER = LoggerFactory.getLogger(NumberInput::class)
    }
}
