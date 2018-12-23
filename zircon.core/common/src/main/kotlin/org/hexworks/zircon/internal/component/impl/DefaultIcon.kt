package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.input.Input

class DefaultIcon(componentMetadata: ComponentMetadata,
                  initialIcon: GraphicTile,
                  private val renderingStrategy: ComponentRenderingStrategy<Icon>)
    : Icon, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val iconProperty = createPropertyFrom(initialIcon).apply {
        onChange {
            render()
        }
    }

    override val icon: GraphicTile
        get() = iconProperty.value

    init {
        render()
    }

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSets.empty()
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }
}
