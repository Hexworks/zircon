package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate

open class DefaultPanel(componentMetadata: ComponentMetadata,
                        initialTitle: String,
                        private val renderingStrategy: ComponentRenderingStrategy<Panel>)
    : Panel, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val titleProperty = createPropertyFrom(initialTitle).also {
        it.onChange {
            render()
        }
    }

    override val title: String
        get() = titleProperty.value

    init {
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to Panel (id=${id.abbreviate()}).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    final override fun render() {
        LOGGER.debug("Panel (id=${id.abbreviate()},visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Panel::class)
    }
}
