package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

@Suppress("DuplicatedCode")
class DefaultButton(
        componentMetadata: ComponentMetadata,
        initialText: String,
        renderingStrategy: ComponentRenderingStrategy<Button>
) : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText) {

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractableStyle()

}
