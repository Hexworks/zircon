package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentStyleSets
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.abbreviate

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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Trying to apply color theme ($colorTheme) to non-themeable Icon (id=${id.abbreviate()}). Operation ignored.")
        return ComponentStyleSets.empty()
    }

    override fun render() {
        LOGGER.debug("Icon (id=${id.abbreviate()}, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Icon::class)
    }
}
