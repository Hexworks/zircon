package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.RadioButton
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*
import kotlin.collections.LinkedHashMap

class DefaultRadioButtonGroup @JvmOverloads constructor(wrappers: Deque<WrappingStrategy>,
                                                        private val size: Size,
                                                        position: Position,
                                                        componentStyles: ComponentStyles,
                                                        scrollable: Scrollable = DefaultScrollable(size, size))
    : RadioButtonGroup, Scrollable by scrollable, DefaultContainer(initialSize = size,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers) {

    private val items = LinkedHashMap<String, RadioButton>()
    private var selectedItem: Optional<String> = Optional.empty()

    init {
        refreshContent()
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), { (mouseAction) ->
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            val row = mouseAction.position - getPosition()
            println("Row is: $row")
            refreshContent()
            EventBus.emit(EventType.ComponentChange)
        })
    }

    override fun addOption(key: String, text: String) {
        if (items.size < size.columns) {
            DefaultRadioButton(
                    text = text,
                    wrappers = LinkedList(),
                    width = size.columns,
                    position = Position.of(0, items.size),
                    componentStyles = getComponentStyles()).let { button ->
                items[key] = button
                addComponent(button)
                EventBus.subscribe<MouseAction>(EventType.MouseReleased(button.getId()), {
                    selectedItem.map {
                        println("Removing selection: $it")
                        items[it]?.removeSelection()
                    }
                    println("Selecting $key")
                    selectedItem = Optional.of(key)
                })
            }
        }
    }

    override fun removeOption(key: String) {
        TODO("Not implemented yet")
    }

    override fun getSelectedOption() = selectedItem

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        EventBus.emit(EventType.ComponentChange)
        return true
    }

    override fun takeFocus(input: Optional<Input>) {
        getDrawSurface().applyStyle(getComponentStyles().reset())
        EventBus.emit(EventType.ComponentChange)
    }

    override fun applyTheme(theme: Theme) {
        val styles = ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getAccentColor())
                        .backgroundColor(TextColorFactory.TRANSPARENT)
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightBackgroundColor())
                        .backgroundColor(theme.getAccentColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightBackgroundColor())
                        .backgroundColor(theme.getAccentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightBackgroundColor())
                        .backgroundColor(theme.getAccentColor())
                        .build())
                .build()
        setComponentStyles(styles)
        getComponents().forEach { it.setComponentStyles(styles) }
    }

    private fun refreshContent() {
        items.forEach { key, comp ->
            removeComponent(comp)
        }
        items.forEach { key, comp ->
            addComponent(comp)
        }
    }
}