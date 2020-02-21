package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate

@Suppress("DuplicatedCode")
class DefaultButton(
        componentMetadata: ComponentMetadata,
        initialText: String,
        renderingStrategy: ComponentRenderingStrategy<Button>
) : Button, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText) {


    init {
        render()
        textProperty.onChange {
            LOGGER.debug("Text property of Button (id=${id.abbreviate()}, disabled=$isDisabled, text=$text)" +
                    " changed from '${it.oldValue}' to '${it.newValue}'.")
            render()
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractableStyle()

    companion object {
        val LOGGER = LoggerFactory.getLogger(Button::class)
    }
}
