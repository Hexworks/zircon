package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

public class DefaultButton internal constructor(
    componentMetadata: ComponentMetadata,
    textProperty: Property<String>,
    renderingStrategy: ComponentRenderingStrategy<Button>
) : Button, TextOverride by TextOverride.create(textProperty), DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = colorTheme.toInteractiveStyle()

}
