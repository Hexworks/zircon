package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

class DefaultButton(
        componentMetadata: ComponentMetadata,
        initialText: String,
        renderingStrategy: ComponentRenderingStrategy<Button>
) : Button, TextOverride by TextOverride.create(initialText), DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy
) {

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractableStyle()

}
