package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

class RootContainer(componentMetadata: ComponentMetadata,
                    private val renderingStrategy: ComponentRenderingStrategy<RootContainer>)
    : Container, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    init {
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        val css = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                        .build())
                .build()
        componentStyleSet = css
        render()
        children.forEach {
            it.applyColorTheme(colorTheme)
        }
        return css
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics)
    }
}
