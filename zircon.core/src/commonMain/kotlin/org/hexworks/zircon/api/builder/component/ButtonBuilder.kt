package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
class ButtonBuilder(
        private var text: String = "")
    : BaseComponentBuilder<Button, ButtonBuilder>(DefaultButtonRenderer()) {

    init {
        withDecorations(side())
    }

    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
                .withWidth(max(text.length, contentSize.width))
    }

    override fun build(): Button {
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = decorationRenderers,
                componentRenderer = componentRenderer as ComponentRenderer<Button>)
        return DefaultButton(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = componentRenderer).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
            .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}
