package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.RadioButton
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.component.RadioButtonGroup.Selection
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.util.ThreadSafeQueue
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultRadioButtonGroup constructor(wrappers: ThreadSafeQueue<WrappingStrategy>,
                                          private val size: Size,
                                          initialTileset: Tileset,
                                          position: Position,
                                          componentStyleSet: ComponentStyleSet,
                                          scrollable: Scrollable = DefaultScrollable(size, size))
    : RadioButtonGroup, Scrollable by scrollable, DefaultContainer(initialSize = size,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialTileset = initialTileset) {

    private val items = LinkedHashMap<String, DefaultRadioButton>()
    private val selectionListeners = mutableListOf<Consumer<Selection>>()
    private var selectedItem: Maybe<String> = Maybe.empty()

    init {
        refreshContent()
        EventBus.listenTo<Event.MouseReleased>(getId()) {
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            refreshContent()
            EventBus.broadcast(Event.ComponentChange)
        }
    }

    override fun addOption(key: String, text: String): RadioButton {
        require(items.size + 1 < size.xLength) {
            "This RadioButtonGroup does not have enough space for another option!"
        }
        return DefaultRadioButton(
                text = text,
                wrappers = ThreadSafeQueueFactory.create(),
                width = size.xLength,
                position = Position.create(0, items.size),
                componentStyleSet = getComponentStyles(),
                initialTileset = getCurrentFont()).also { button ->
            items[key] = button
            addComponent(button)
            EventBus.listenTo<Event.MouseReleased>(button.getId()) {
                selectedItem.map { lastSelected ->
                    if (lastSelected != key) {
                        items[lastSelected]?.removeSelection()
                    }
                }
                selectedItem = Maybe.of(key)
                items[key]?.let { button ->
                    button.select()
                    selectionListeners.forEach {
                        it.accept(DefaultSelection(key, button.getText()))
                    }
                }

            }
        }
    }

    override fun getSelectedOption() = selectedItem

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun clearSelection() =
            if (selectedItem.isPresent) {
                items[selectedItem.get()]?.removeSelection() ?: false
            } else {
                false
            }

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(TextColor.transparent())
                        .backgroundColor(TextColor.transparent())
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
            comp.setPosition(Position.create(0, idx))
            addComponent(comp)
        }
    }

    data class DefaultSelection(private val key: String,
                                private val value: String) : Selection {
        override fun getKey() = key

        override fun getValue() = value

    }
}
