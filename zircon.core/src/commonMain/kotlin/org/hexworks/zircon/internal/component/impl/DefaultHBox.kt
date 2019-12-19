package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.TitleHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.abbreviate

open class DefaultHBox(componentMetadata: ComponentMetadata,
                       initialTitle: String,
                       private val spacing: Int,
                       private val renderingStrategy: ComponentRenderingStrategy<HBox>)
    : HBox, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TitleHolder by TitleHolder.create(initialTitle) {

    private var filledUntil = Position.create(0, 0)
    private var availableSpace = contentSize.toRect()

    init {
        render()
    }

    override fun addComponent(component: Component) {
        checkAvailableSpace(component)
        val finalSpacing = if (children.isEmpty()) 0 else spacing
        val finalSize = component.width + finalSpacing
        component.moveRightBy(filledUntil.x + finalSpacing)
        filledUntil = filledUntil.withRelativeX(finalSize)
        availableSpace = availableSpace.withRelativeWidth(-finalSize)
        super<DefaultContainer>.addComponent(component)
    }

    override fun removeComponent(component: Component): Boolean {
        val result = super.removeComponent(component)
        if (result) {
            reorganizeComponents(component)
        }
        return result
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .build()
    }

    final override fun render() {
        LOGGER.debug("HBox (id=${id.abbreviate()}, hidden=$isHidden) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    private fun reorganizeComponents(component: Component) {
        val width = component.width
        val delta = width + if (children.isEmpty()) 0 else spacing
        val x = component.position.x
        children.filter {
            it.position.x >= x
        }.forEach {
            it.moveLeftBy(delta)
        }
        filledUntil = filledUntil.withRelativeX(-delta)
        availableSpace = availableSpace.withRelativeWidth(delta)
    }

    private fun checkAvailableSpace(component: Component) =
            require(availableSpace.withRelativeWidth(-spacing).containsBoundable(component.rect)) {
                "There is not enough space ($availableSpace) left for the component: $component."
            }

    companion object {
        val LOGGER = LoggerFactory.getLogger(HBox::class)
    }
}
