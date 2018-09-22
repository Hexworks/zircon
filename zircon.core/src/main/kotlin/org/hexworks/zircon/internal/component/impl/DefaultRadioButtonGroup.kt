package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.internal.event.ZirconEvent

class DefaultRadioButtonGroup constructor(
        private val renderingStrategy: ComponentRenderingStrategy<RadioButtonGroup>,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size))
    : RadioButtonGroup, Scrollable by scrollable, DefaultContainer(
        size = size,
        position = position,
        componentStyles = componentStyleSet,
        tileset = tileset) {

    private val items = LinkedHashMap<String, DefaultRadioButton>()
    private val selectionListeners = mutableListOf<Consumer<Selection>>()
    private var selectedItem: Maybe<String> = Maybe.empty()
    private val buttonRenderingStrategy = DefaultComponentRenderingStrategy(
            decorationRenderers = listOf(),
            componentRenderer = DefaultRadioButtonRenderer())

    init {
        refreshContent()
        render()
    }

    override fun addOption(key: String, text: String): RadioButton {
        require(items.size < renderingStrategy.effectiveSize(size()).yLength) {
            "This RadioButtonGroup does not have enough space for another option!"
        }
        return DefaultRadioButton(
                text = text,
                renderingStrategy = buttonRenderingStrategy,
                size = Size.create(renderingStrategy.effectiveSize(size()).width(), 1),
                position = Position.create(0, items.size) + renderingStrategy.effectivePosition(),
                componentStyleSet = componentStyleSet(),
                tileset = tileset()).also { button ->
            items[key] = button
            addComponent(button)
            EventBus.listenTo<ZirconEvent.MouseReleased>(button.id) { _ ->
                selectedItem.map { lastSelected ->
                    if (lastSelected != key) {
                        items[lastSelected]?.removeSelection()
                    }
                }
                selectedItem = Maybe.of(key)
                items[key]?.let { button ->
                    button.select()
                    selectionListeners.forEach {
                        it.accept(DefaultSelection(key, button.text()))
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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryForegroundColor())
                        .backgroundColor(colorTheme.primaryBackgroundColor())
                        .build())
                .build().also { css ->
                    setComponentStyleSet(css)
                    render()
                    getComponents().forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun onSelection(callback: Consumer<Selection>) {
        selectionListeners.add(callback)
    }

    private fun refreshContent() {
        items.values.forEach {
            removeComponent(it)
        }
        items.values.forEachIndexed { idx, comp ->
            comp.moveTo(Position.create(0, idx))
            addComponent(comp)
        }
        render()
    }

    private fun render() {
        renderingStrategy.render(this, tileGraphics())
    }

    data class DefaultSelection(private val key: String,
                                private val value: String) : Selection {
        override fun getKey() = key

        override fun getValue() = value

    }
}
