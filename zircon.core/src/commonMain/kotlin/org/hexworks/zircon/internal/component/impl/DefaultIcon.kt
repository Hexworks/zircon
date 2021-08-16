package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Tile

class DefaultIcon internal constructor(
    componentMetadata: ComponentMetadata,
    override val iconProperty: Property<Tile>,
    renderingStrategy: ComponentRenderingStrategy<Icon>
) : Icon, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override var icon: Tile by iconProperty.asDelegate()

    override fun acceptsFocus() = false

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSets.empty()

}
