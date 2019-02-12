package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalContainer

class RootContainer(componentMetadata: ComponentMetadata,
                    private val renderingStrategy: ComponentRenderingStrategy<RootContainer>)
    : Container, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    init {
        render()
    }

    // a RootContainer is always attached
    override fun isAttached() = true

    override fun acceptsFocus() = true

    override fun focusGiven() = Processed

    override fun focusTaken() = Processed

    override fun calculatePathFromRoot() = listOf<InternalContainer>(this)

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        // we don't need to call render here because a component is automatically
        // rendered when its style changes
        componentStyleSet = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                        .build())
                .build()
        children.forEach {
            it.applyColorTheme(colorTheme)
        }
        return componentStyleSet
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }
}
