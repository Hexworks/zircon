package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.ComponentDecorations.noDecoration
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.dsl.component.label
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.graphics.Symbols

class DefaultSelector<T : Any> internal constructor(
    position: Position,
    width: Int,
    defaultSelected: T,
    override val valuesProperty: ListProperty<T>,
    private val centeredText: Boolean = true,
    private val toStringMethod: (T) -> String = Any::toString,
    clickable: Boolean = false
) : Selector<T> {

    override val values: List<T> by valuesProperty.asDelegate()

    private val indexProperty = values.indexOf(defaultSelected).toProperty()


    override val selectedValue: ObservableValue<T> = indexProperty.bindTransform {
        values[it]
    }
    override val selected: T
        get() = selectedValue.value

    private lateinit var leftButton: Button
    private lateinit var rightButton: Button

    private val labelSize: Size = Size.create(width - 2, 1)

    override val root: HBox = buildHbox {
        preferredSize = Size.create(width, 1)
        this.position = position
        leftButton = button {
            +Symbols.ARROW_LEFT.toString()
            decoration = noDecoration()
            onActivated {
                showPrevValue()
            }
        }

        if (clickable) {
            button {
                decoration = noDecoration()
                preferredSize = labelSize
                +fetchLabelBy(0)
                textProperty.updateFrom(indexProperty) { i -> fetchLabelBy(i) }
                onActivated {
                    showNextValue()
                }
            }
        } else {
            label {
                preferredSize = labelSize
                +fetchLabelBy(0)
                textProperty.updateFrom(indexProperty) { i -> fetchLabelBy(i) }
            }
        }

        rightButton = button {
            +Symbols.ARROW_RIGHT.toString()
            decoration = noDecoration()
            onActivated {
                showNextValue()
            }
        }
    }

    private fun setValue(to: Int) {
        indexProperty.value = to
    }

    private fun showNextValue() {
        val oldIndex = indexProperty.value
        var nextIndex = oldIndex + 1
        if (nextIndex >= values.size) {
            nextIndex = 0
        }
        setValue(nextIndex)
    }

    private fun showPrevValue() {
        val oldIndex = indexProperty.value
        var prevIndex = oldIndex - 1
        if (prevIndex < 0) {
            prevIndex = values.size - 1
        }
        setValue(prevIndex)
    }

    private fun fetchLabelBy(index: Int) = toStringMethod.invoke(values[index]).centered()

    private fun String.centered(): String {
        val maxWidth = labelSize.width
        return if (centeredText && length < maxWidth) {
            val spacesCount = (maxWidth - length) / 2
            this.padStart(spacesCount + length).padEnd(maxWidth)
        } else {
            this.substring(0, kotlin.math.min(length, maxWidth))
        }
    }

}
