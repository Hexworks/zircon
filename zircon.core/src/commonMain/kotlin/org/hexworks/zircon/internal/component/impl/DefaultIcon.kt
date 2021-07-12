package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Tile

class DefaultIcon(
    componentMetadata: ComponentMetadata,
    initialIcon: Tile,
    renderingStrategy: ComponentRenderingStrategy<Icon>
) : Icon, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override val iconProperty = initialIcon.toProperty()
    override var icon: Tile by iconProperty.asDelegate()

    override fun acceptsFocus() = false

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSets.empty()

}
