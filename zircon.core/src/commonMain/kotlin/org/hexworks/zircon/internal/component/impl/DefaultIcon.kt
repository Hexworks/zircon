package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Tile

class DefaultIcon(componentMetadata: ComponentMetadata,
                  initialIcon: Tile,
                  private val renderingStrategy: ComponentRenderingStrategy<Icon>)
    : Icon, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val iconProperty = createPropertyFrom(initialIcon)
    override var icon: Tile by iconProperty.asDelegate()

    init {
        render()
        iconProperty.onChange {
            render()
        }
    }

    override fun acceptsFocus() = false

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSets.empty()

}
