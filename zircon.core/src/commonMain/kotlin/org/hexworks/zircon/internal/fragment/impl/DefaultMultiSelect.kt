package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType

class DefaultMultiSelect<T : Any>(
        width: Int,
        override val values: List<T>,
        val callback: (oldValue: T, newValue: T) -> Unit,
        private val centeredText: Boolean = true,
        private val toStringMethod: (T) -> String = Any::toString,
        clickable: Boolean = false
) : MultiSelect<T> {

    init {
        if (values.isEmpty()) {
            throw IllegalStateException("Values list may not be empty")
        }
        val minWidth = 3
        if (width < minWidth) {
            throw IllegalStateException("MultiSelect needs a minimum width of $minWidth, given was $width")
        }
    }

    private val indexProperty = createPropertyFrom(0)

    private val rightButton = Components.
            button().
            withText(Symbols.ARROW_RIGHT.toString()).
            withDecorations().
            build().
            apply {
                processComponentEvents(ComponentEventType.ACTIVATED) { nextValue() }
            }

    private val leftButton = Components.
            button().
            withText(Symbols.ARROW_LEFT.toString()).
            withDecorations().
            build().
            apply {
                processComponentEvents(ComponentEventType.ACTIVATED) { prevValue() }
            }

    private val label = Components.
            label().
            withSize(width - (leftButton.width + rightButton.width), 1).
            build()

    private val buttonLabel = Components.
            button().
            withDecorations().
            withSize(label.size).
            build()

    override val root = Components.
            hbox().
            withSize(width, 1).
            withSpacing(0).
            build().
            apply {
                addComponent(leftButton)

                if(clickable) {
                    addComponent(buttonLabel.also { it.initLabel() })
                    buttonLabel.processComponentEvents(ComponentEventType.ACTIVATED) {
                        setValue(indexProperty.value, indexProperty.value)
                    }
                } else {
                    addComponent(label.also { it.initLabel() })
                }

                addComponent(rightButton)
            }

    private fun TextHolder.initLabel() {
        text = getStringValue(0)
        textProperty.updateFrom(indexProperty) { i -> getStringValue(i) }
    }

    private fun setValue(from: Int, to: Int) {
        indexProperty.value = to
        callback.invoke(values[from], values[indexProperty.value])
    }

    private fun nextValue() {
        val oldIndex = indexProperty.value
        var nextIndex = oldIndex + 1
        if (nextIndex >= values.size) {
            nextIndex = 0
        }
        setValue(oldIndex, nextIndex)
    }

    private fun prevValue() {
        val oldIndex = indexProperty.value
        var prevIndex = oldIndex - 1
        if (prevIndex < 0) {
            prevIndex = values.size - 1
        }
        setValue(oldIndex, prevIndex)
    }

    private fun getStringValue(index: Int) = toStringMethod.invoke(values[index]).centered()

    private fun String.centered(): String {
        val maxWidth = label.contentSize.width
        return if (centeredText && length < maxWidth) {
            val spacesCount = (maxWidth - length) / 2
            this.padStart(spacesCount + length).padEnd(maxWidth)
        } else {
            this.substring(0, kotlin.math.min(length, maxWidth))
        }
    }
}
