package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory

class DefaultRadioButtonGroup constructor(
        private val renderingStrategy: ComponentRenderingStrategy<RadioButtonGroup>,
        position: Position,
        size: Size,
        tileset: TilesetResource,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size))
    : RadioButtonGroup, Scrollable by scrollable, DefaultContainer(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private val items = ThreadSafeMapFactory.create<String, DefaultRadioButton>()
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
        require(items.size < renderingStrategy.contentSize(size()).yLength) {
            "This RadioButtonGroup does not have enough space for another option!"
        }
        return DefaultRadioButton(
                text = text,
                renderingStrategy = buttonRenderingStrategy,
                size = Size.create(renderingStrategy.contentSize(size()).width(), 1),
                position = Position.create(0, items.size),
                componentStyleSet = componentStyleSet(),
                tileset = currentTileset()).also { button ->
            items[key] = button
            button.onMouseReleased { _ ->
                selectedItem.map { lastSelected ->
                    if (lastSelected != key) {
                        println("Removing selection from $lastSelected")
                        items[lastSelected]?.removeSelection()
                    }
                }
                selectedItem = Maybe.of(key)
                items[key]?.let { button ->
                    selectionListeners.forEach {
                        it.accept(DefaultSelection(key, button.text()))
                    }
                }
            }
            addComponent(button)
        }
    }

    override fun getSelectedOption() = selectedItem

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun clearSelection() {
        items[selectedItem.get()]?.removeSelection()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .build().also { css ->
                    setComponentStyleSet(css)
                    render()
                    children().forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun onSelection(callback: Consumer<Selection>) {
        selectionListeners.add(callback)
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
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

    data class DefaultSelection(private val key: String,
                                private val value: String) : Selection {
        override fun getKey() = key

        override fun getValue() = value

    }
}
