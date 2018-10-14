package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

class DefaultTextBox(componentMetadata: ComponentMetadata,
                     private val renderingStrategy: ComponentRenderingStrategy<DefaultTextBox>)
    : TextBox, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    init {
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }
}
