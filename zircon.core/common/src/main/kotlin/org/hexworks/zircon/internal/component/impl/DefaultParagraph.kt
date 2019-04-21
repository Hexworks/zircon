package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate

class DefaultParagraph(componentMetadata: ComponentMetadata,
                       initialText: String,
                       private val renderingStrategy: ComponentRenderingStrategy<Paragraph>)
    : Paragraph, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText) {

    init {
        render()
        textProperty.onChange {
            render()
        }
    }

    override fun acceptsFocus() = false

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun render() {
        LOGGER.debug("Paragraph (id=${id.abbreviate()},visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Paragraph::class)
    }
}
