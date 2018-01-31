package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.RadioButton
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.component.RadioButtonGroup.Selection
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*
import java.util.function.Consumer
import kotlin.collections.LinkedHashMap

class DefaultRadioButtonGroup @JvmOverloads constructor(wrappers: Deque<WrappingStrategy>,
                                                        private val size: Size,
                                                        initialFont: Font,
                                                        position: Position,
                                                        componentStyles: ComponentStyles,
                                                        scrollable: Scrollable = DefaultScrollable(size, size))
    : RadioButtonGroup, Scrollable by scrollable, DefaultContainer(initialSize = size,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers,
        initialFont = initialFont) {

    private val items = LinkedHashMap<String, DefaultRadioButton>()
    private val selectionListeners = mutableListOf<Consumer<Selection>>()
    private var selectedItem: Optional<String> = Optional.empty()

    init {
        refreshContent()
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), { (_) ->
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            refreshContent()
            EventBus.emit(EventType.ComponentChange)
        })
    }

    override fun addOption(key: String, text: String): RadioButton {
        require(items.size + 1 < size.columns) {
            "This RadioButtonGroup does not have enough space for another option!"
        }
        return DefaultRadioButton(
                text = text,
                wrappers = LinkedList(),
                width = size.columns,
                position = Position.of(0, items.size),
                componentStyles = getComponentStyles(),
                initialFont = getCurrentFont()).also { button ->
            items[key] = button
            addComponent(button)
            EventBus.subscribe<MouseAction>(EventType.MouseReleased(button.getId()), {
                selectedItem.map { lastSelected ->
                    if (lastSelected != key) {
                        items[lastSelected]?.removeSelection()
                    }
                }
                selectedItem = Optional.of(key)
                items[key]?.let { button ->
                    button.select()
                    selectionListeners.forEach {
                        it.accept(DefaultSelection(key, button.getText()))
                    }
                }
            })
        }
    }

    override fun getSelectedOption() = selectedItem

    override fun acceptsFocus() = false

    override fun giveFocus(input: Optional<Input>) = false

    override fun takeFocus(input: Optional<Input>) {}

    override fun clearSelection() =
            if (selectedItem.isPresent) {
                items[selectedItem.get()]?.removeSelection() ?: false
            } else {
                false
            }

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(TextColorFactory.TRANSPARENT)
                        .backgroundColor(TextColorFactory.TRANSPARENT)
                        .build())
                .build())
        getComponents().forEach { it.applyColorTheme(colorTheme) }
    }

    override fun onSelection(callback: Consumer<Selection>) {
        selectionListeners.add(callback)
    }

    private fun refreshContent() {
        items.values.forEach {
            removeComponent(it)
        }
        items.values.forEachIndexed { idx, comp ->
            comp.setPosition(Position.of(0, idx))
            addComponent(comp)
        }
    }

    data class DefaultSelection(private val key: String,
                                private val value: String) : Selection {
        override fun getKey() = key

        override fun getValue() = value

    }
}
