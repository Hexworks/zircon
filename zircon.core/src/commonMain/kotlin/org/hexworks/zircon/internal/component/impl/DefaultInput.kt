package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Input
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.util.isNavigationKey
import org.hexworks.zircon.api.util.isPrintableCharacter
import org.hexworks.zircon.internal.component.extensions.whenConnectedToRoot
import org.hexworks.zircon.internal.event.ZirconEvent

public class DefaultInput internal constructor(
    componentMetadata: ComponentMetadata,
    override val textProperty: Property<String>,
    override val maxLength: Int?,
    renderingStrategy: ComponentRenderingStrategy<Input>
) : Input, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override var text: String
        get() = textProperty.value
        set(value) {
            textProperty.value = value.limited()
        }

    private val cursorProperty: Property<Int> = text.length.toProperty()
    override var cursorIndex: Int by cursorProperty.asDelegate()
        private set

    private val scrollProperty: Property<Int> = 0.toProperty()
    override var scrollOffset: Int by scrollProperty.asDelegate()
        private set

    override val renderInputs: List<ObservableValue<*>> get() = listOf(textProperty, cursorProperty, scrollProperty)

    init {
        scrollIntoView()
        textProperty.onChange { (_, newText) ->
            // Text set from the outside: the cursor goes to the end, like a fresh field.
            if (cursorIndex > newText.length) cursorIndex = newText.length
            scrollIntoView()
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = colorTheme.secondaryBackgroundColor
            backgroundColor = colorTheme.secondaryForegroundColor
        }
        disabledStyle = styleSet {
            foregroundColor = colorTheme.secondaryForegroundColor
            backgroundColor = Color.TRANSPARENT
        }
        // The same three, so the field does not flicker on hover while focused.
        focusedStyle = styleSet {
            foregroundColor = colorTheme.primaryBackgroundColor
            backgroundColor = colorTheme.primaryForegroundColor
        }
        highlightedStyle = focusedStyle
        activeStyle = focusedStyle
    }

    override fun focusGiven(): UIEventResponse = whenEnabled {
        scrollIntoView()
        super.focusGiven()
    }

    override fun focusTaken(): UIEventResponse = whenEnabled {
        whenConnectedToRoot { root ->
            root.eventBus.publish(ZirconEvent.HideCursor(this), root.eventScope)
        }
        super.focusTaken()
    }

    // Space types a space, it must not toggle the ACTIVE state.
    override fun activated(): UIEventResponse = Processed
    override fun deactivated(): UIEventResponse = Processed

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase != UIEventPhase.TARGET || event.isNavigationKey()) return@whenEnabledRespondWith Pass
        when (event.code) {
            KeyCode.LEFT -> moveCursorTo(cursorIndex - 1)
            KeyCode.RIGHT -> moveCursorTo(cursorIndex + 1)
            KeyCode.HOME -> moveCursorTo(0)
            KeyCode.END -> moveCursorTo(text.length)
            KeyCode.BACKSPACE -> if (cursorIndex > 0) {
                val index = cursorIndex - 1
                textProperty.value = text.removeRange(index, index + 1)
                moveCursorTo(index)
            }
            KeyCode.DELETE -> if (cursorIndex < text.length) {
                textProperty.value = text.removeRange(cursorIndex, cursorIndex + 1)
            }
            // Enter belongs to whatever the screen does with it (a default button, say).
            KeyCode.ENTER -> return@whenEnabledRespondWith Pass
            else -> {
                val typed = event.key.filter { it.isPrintableCharacter() && it != '\n' && it != '\r' }
                if (typed.isEmpty()) return@whenEnabledRespondWith Pass
                val room = maxLength?.let { it - text.length } ?: typed.length
                val insert = typed.take(maxOf(0, room))
                if (insert.isNotEmpty()) {
                    textProperty.value = text.substring(0, cursorIndex) + insert + text.substring(cursorIndex)
                    moveCursorTo(cursorIndex + insert.length)
                }
            }
        }
        scrollIntoView()
        Processed
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        val column = event.position.x - (absolutePosition.x + contentOffset.x)
        moveCursorTo(scrollOffset + column)
        super.mouseReleased(event, phase)
    }

    private fun moveCursorTo(index: Int) {
        cursorIndex = index.coerceIn(0, text.length)
        scrollIntoView()
    }

    /**
     * Keeps the cursor inside the visible columns, with the last column kept free for it
     * when the cursor is at the end of the text.
     */
    private fun scrollIntoView() {
        val width = contentSize.width
        if (width <= 0) return
        if (cursorIndex < scrollOffset) {
            scrollOffset = cursorIndex
        } else if (cursorIndex >= scrollOffset + width) {
            scrollOffset = cursorIndex - width + 1
        }
        scrollOffset = scrollOffset.coerceIn(0, maxOf(0, text.length - width + 1))
    }

    private fun String.limited(): String = maxLength?.let { take(it) } ?: this
}
