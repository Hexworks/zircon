package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.kotlin.onSelected
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory

class DefaultRadioButtonGroup constructor(
        componentMetadata: ComponentMetadata,
        private val renderingStrategy: ComponentRenderingStrategy<RadioButtonGroup>)
    : RadioButtonGroup,
        Scrollable by DefaultScrollable(componentMetadata.size, componentMetadata.size),
        Observable<Selection> by DefaultObservable(),
        DefaultContainer(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    private val items = ThreadSafeMapFactory.create<String, DefaultRadioButton>()
    private var selectedItem: Maybe<String> = Maybe.empty()
    private val buttonRenderingStrategy = DefaultComponentRenderingStrategy(
            decorationRenderers = listOf(),
            componentRenderer = DefaultRadioButtonRenderer())

    init {
        refreshContent()
    }

    override fun addOption(key: String, text: String): RadioButton {
        require(items.size < renderingStrategy.calculateContentSize(size).height) {
            "This RadioButtonGroup does not have enough space for another option!"
        }
        return DefaultRadioButton(
                initialText = text,
                renderingStrategy = buttonRenderingStrategy,
                componentMetadata = ComponentMetadata(
                        position = Position.create(0, items.size),
                        size = Size.create(renderingStrategy.calculateContentSize(size).width, 1),
                        tileset = currentTileset(),
                        componentStyleSet = componentStyleSet)).also { button ->
            items[key] = button
            button.onSelected {
                selectedItem.map { lastSelected ->
                    if (lastSelected != key) {
                        println("Removing selection from $lastSelected")
                        items[lastSelected]?.removeSelection()
                    }
                }
                selectedItem = Maybe.of(key)
                items[key]?.let { button ->
                    notifyObservers(DefaultSelection(key, button.text))
                }
            }
            addComponent(button)
        }
    }

    override fun fetchSelectedOption() = selectedItem

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun clearSelection() {
        selectedItem.map {
            selectedItem = Maybe.empty()
            items[it]?.removeSelection()
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun onSelection(callback: Consumer<Selection>) {
        addObserver(callback)
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
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

    data class DefaultSelection(override val key: String,
                                override val value: String) : Selection
}
